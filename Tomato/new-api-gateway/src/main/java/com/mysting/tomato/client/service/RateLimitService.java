package com.mysting.tomato.client.service;

public interface RateLimitService {

	public boolean checkRateLimit(String reqUrl, String accessToken) ;
	
}
