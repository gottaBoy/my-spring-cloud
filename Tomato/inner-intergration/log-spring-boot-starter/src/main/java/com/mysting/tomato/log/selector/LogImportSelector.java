package com.mysting.tomato.log.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * log-spring-boot-starter 自动装配
 */


public class LogImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		
		return new String[] { 
				"com.mysting.tomato.log.aop.LogAnnotationAOP",
//				"com.mysting.tomato.log.config.SentryAutoConfig",
				"com.mysting.tomato.log.service.impl.LogServiceImpl",
				"com.mysting.tomato.log.config.LogAutoConfig"
				
		};
	}

}