package com.mysting.tomato.common.feign;

import lombok.Data;

@Data
public class FeignFailResult {
	
	 private int status;
	 private String msg;
}
