package com.mysting.tomato.oss.model;

/**
 * 仅支持阿里云 oss ,七牛云等
*/
public enum FileType {
//	七牛
	QINIU ,
//	阿里云
	ALIYUN,
	// 本地存储
	LOCAL, 
	//fastdfs存储
	FASTDFS
}
