package com.mysting.tomato.redis.prefix;

public interface KeyPrefix {
		
	public int expireSeconds();
	
	public String getPrefix();
	
}
