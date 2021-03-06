package com.mysting.tomato.oss.service;

import com.mysting.tomato.common.web.PageResult;
import com.mysting.tomato.oss.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件service 目前仅支持阿里云oss,七牛云
*/
public interface FileService {

	FileInfo upload(MultipartFile file ) throws Exception;

	void delete(FileInfo fileInfo);
	
	FileInfo getById(String id);
	
	PageResult<FileInfo>  findList(Map<String, Object> params);

	void unZip(String filePath, String descDir) throws RuntimeException ;

	void chunk(String guid, Integer chunk, MultipartFile file, Integer chunks,String filePath) throws Exception;

	FileInfo merge(String guid,String fileName,String filePath) throws Exception;

	void uploadError(String guid,String fileName,String filePath) throws Exception;

}
