package com.mysting.tomato.sms.service;

import java.util.Map;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.sms.model.Sms;

/**
 * @author 作者 owen 
 * @version 创建时间：2017年11月12日 上午22:57:51
 * 短信service 
 */
public interface SmsService {

	/**
	 * 保存短信
	 * @param sms
	 * @param params
	 */
	void save(Sms sms, Map<String, String> params);

	/**
	 * 修改短信
	 * @param sms
	 */
	void update(Sms sms);

	/**
	 * 查询短信
	 * @param id
	 * @return
	 */
	Sms findById(Long id);

	/**
	 * 短信列表
	 * @param params
	 * @return
	 */
	PageResult<Sms> findSms(Map<String, Object> params);

	/**
	 * 发送短信
	 */
	SendSmsResponse sendSmsMsg(Sms sms);
}
