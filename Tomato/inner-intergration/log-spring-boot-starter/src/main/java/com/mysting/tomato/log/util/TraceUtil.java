package com.mysting.tomato.log.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mysting.tomato.common.constant.TraceConstant;

/**
 * 
 * api 经过filter--> interceptor -->aop -->controller
 * 如果某些接口，比如filter --> userdetail 这种情况，aop mdc设置 后续log输出traceid
 */
public class TraceUtil {

	public static String getTrace() {
		String appTraceId = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();

			appTraceId = request.getHeader(TraceConstant.HTTP_HEADER_TRACE_ID);

			// 未经过HandlerInterceptor的设置
			if (StringUtils.isBlank(MDC.get(TraceConstant.LOG_TRACE_ID))) {
				// 但是有请求头，重新设置
				if (StringUtils.isNotEmpty(appTraceId)) {
					MDC.put(TraceConstant.LOG_TRACE_ID, appTraceId);
				}
			}
		} catch (Exception e) {

		}

		return appTraceId;

	}

}
