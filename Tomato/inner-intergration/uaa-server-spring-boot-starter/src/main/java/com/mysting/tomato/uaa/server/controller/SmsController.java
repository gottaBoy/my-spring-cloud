package com.mysting.tomato.uaa.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysting.tomato.common.util.StringUtil;
import com.mysting.tomato.common.web.ResponseEntity;
import com.mysting.tomato.log.annotation.LogAnnotation;
import com.mysting.tomato.uaa.server.service.ValidateCodeService;

/**
 * 短信提供
 */
@RestController
@SuppressWarnings("all")
public class SmsController {

    public final static String SYSMSG_LOGIN_PWD_MSG="验证码：{0}，3分钟内有效";

    @Autowired
    private ValidateCodeService validateCodeService;

	@RequestMapping("/sms/send")
    @LogAnnotation(module="auth-server",recordRequestParam=false)
    public ResponseEntity sendSms(@RequestParam(value = "mobile",required = false) String mobile) {
		String content = SmsController.SYSMSG_LOGIN_PWD_MSG.replace("{0}", StringUtil.generateRamdomNum());
//        SendMsgResult sendMsgResult = MobileMsgConfig.sendMsg(mobile, content);

        String calidateCode = StringUtil.generateRamdomNum();

        // TODO: 2019-08-29 发送短信验证码 每个公司对接不同，自己实现

        validateCodeService.saveImageCode(mobile, calidateCode);
        return  ResponseEntity.succeed(  calidateCode, "发送成功");
    }

}
