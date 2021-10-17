package com.mysting.tomato.fileclient.service;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.mysting.tomato.oss.model.FileInfo;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(value="file-center" ,configuration=UploadFeignClient.MultipartSupportConfig.class)
public interface UploadFeignClient {

	@PostMapping(value = "/files-anon", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FileInfo upload(@RequestPart("file") MultipartFile file) ;
	
	@Configuration
	class MultipartSupportConfig {
        @Autowired
		private ObjectFactory<HttpMessageConverters> messageConverters;
		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder(new SpringEncoder(messageConverters));
		}
	}

}
