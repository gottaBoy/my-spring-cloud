

package com.xxl.job.admin.core.model;
/** 
* @author 作者 wangwen08 
* @version 创建时间：2020年9月3日 上午10:24:27 
* 类说明 
*/
public class XxlJobAlarm {
	String corpid	="wwc830525c5b018fe7";
    String corpsecret ="dCSk9_GdmtmkC9RcVjVROEBlLne-JPcgUrDiQS5JBbA";
    String getTokenUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret;
    String sendMessageUrl ="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
    String msg = " {"
	    			 + "\"toparty\": \"1\","				//指定部门
	    			 + "\"msgtype\": \"text\","				//指定
	    			 + "\"agentid\": 1000002,"				//指定应用程序
	    			 + "\"text\": {\"content\": \"test\"},"	//指定告警内容
	    			 + "\"safe\": 0,"
	    			 + "\"enable_id_trans\": 0,"
	    			 + "\"enable_duplicate_check\": 0,"
	    			 + "\"duplicate_check_interval\": 1800"
    			 + "}";
    
    
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCorpsecret() {
		return corpsecret;
	}
	public void setCorpsecret(String corpsecret) {
		this.corpsecret = corpsecret;
	}
	public String getGetTokenUrl() {
		return getTokenUrl;
	}
	public void setGetTokenUrl(String getTokenUrl) {
		this.getTokenUrl = getTokenUrl;
	}
	public String getSendMessageUrl() {
		return sendMessageUrl;
	}
	public void setSendMessageUrl(String sendMessageUrl) {
		this.sendMessageUrl = sendMessageUrl;
	}

     
}
