package com.mysting.tomato.redis.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
