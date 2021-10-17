package com.mysting.tomato.client.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.mysting.tomato.client.service.SysClientService;
import com.mysting.tomato.client.utils.RedisLimiterUtils;
import com.mysting.tomato.common.util.TokenUtil;
import com.mysting.tomato.common.web.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

/**
 *    根据应用 url 限流 oauth_client_details if_limit 限流开关
 *    limit_count 阈值
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class RateLimitFilter extends ZuulFilter {

	private ThreadLocal<ResponseEntity> error_info = new ThreadLocal<ResponseEntity>();
	@Autowired
	private RedisLimiterUtils redisLimiterUtils;
	@Autowired
	private ObjectMapper objectMapper;

	@Resource
	SysClientService sysClientService;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		try {
			RequestContext ctx = RequestContext.getCurrentContext();
			HttpServletRequest request = ctx.getRequest();
			if (!checkLimit(request)) {
				log.error("too many requests!");
				error_info.set(ResponseEntity.failedWith(null, 429, "too many requests!"));
				serverResponse(ctx, 429);
				return null;
			}

		} catch (Exception e) {
			log.error("RateLimitFilter->run:{}", e.getMessage());
		}
		return null;
	}

	/***
	 * 统一禁用输出
	 * 
	 * @param ctx
	 * @param ret_message 输出消息
	 * @param http_code   返回码
	 */
	public void serverResponse(RequestContext ctx, int http_code) {

		try {
			ctx.setSendZuulResponse(false);
			outputChineseByOutputStream(ctx.getResponse(), error_info);
			ctx.setResponseStatusCode(http_code);
		} catch (IOException e) {
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			log.error(
					"serverResponse:" + "---|Exception:" + stackTraceElement.getLineNumber() + "----" + e.getMessage());
		}

	}

	/**
	 * 使用OutputStream流输出中文
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	public void outputChineseByOutputStream(HttpServletResponse response, ThreadLocal<ResponseEntity> data) throws IOException {
		/**
		 * 使用OutputStream输出中文注意问题： 在服务器端，数据是以哪个码表输出的，那么就要控制客户端浏览器以相应的码表打开，
		 * 比如：outputStream.write("中国".getBytes("UTF-8"));//使用OutputStream流向客户端浏览器输出中文，以UTF-8的编码进行输出
		 * 此时就要控制客户端浏览器以UTF-8的编码打开，否则显示的时候就会出现中文乱码，那么在服务器端如何控制客户端浏览器以以UTF-8的编码显示数据呢？
		 * 可以通过设置响应头控制浏览器的行为，例如： response.setHeader("content-type",
		 * "text/html;charset=UTF-8");//通过设置响应头控制浏览器以UTF-8的编码显示数据
		 */
		OutputStream outputStream = response.getOutputStream();// 获取OutputStream输出流
		response.setHeader("content-type", "application/json;charset=UTF-8");// 通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
		/**
		 * data.getBytes()是一个将字符转换成字节数组的过程，这个过程中一定会去查码表， 如果是中文的操作系统环境，默认就是查找查GB2312的码表，
		 * 将字符转换成字节数组的过程就是将中文字符转换成GB2312的码表上对应的数字 比如： "中"在GB2312的码表上对应的数字是98
		 * "国"在GB2312的码表上对应的数字是99
		 */
		/**
		 * getBytes()方法如果不带参数，那么就会根据操作系统的语言环境来选择转换码表，如果是中文操作系统，那么就使用GB2312的码表
		 */
		String msg = objectMapper.writeValueAsString(data.get());
		byte[] dataByteArr = msg.getBytes("UTF-8");// 将字符转换成字节数组，指定以UTF-8编码进行转换
		outputStream.write(dataByteArr);// 使用OutputStream流向客户端输出字节数组
	}

	public boolean checkLimit(HttpServletRequest request) {

		// 解决zuul token传递问题
		try {
			String clientId = TokenUtil.getClientId();
			Map client = sysClientService.getClient(clientId);
			if (client != null) {
				String flag = String.valueOf(client.get("ifLimit"));
				if ("1".equals(flag)) {
					String accessLimitCount = String.valueOf(client.get("limitCount"));
					if (!accessLimitCount.isEmpty()) {
						ResponseEntity result = redisLimiterUtils.rateLimitOfDay(clientId, request.getRequestURI(),
								Long.parseLong(accessLimitCount));
						if (-1 == result.getCode()) {
							log.error("token:" + TokenUtil.getToken() + result.getMsg());
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			StackTraceElement stackTraceElement = e.getStackTrace()[0];
			log.error("checkLimit:" + "---|Exception:" + stackTraceElement.getLineNumber() + "----" + e.getMessage());
		}

		return true;
	}
}
