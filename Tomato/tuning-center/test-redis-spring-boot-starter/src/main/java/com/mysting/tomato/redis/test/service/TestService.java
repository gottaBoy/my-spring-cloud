package com.mysting.tomato.redis.test.service;

import java.util.HashMap;
import java.util.Map;

import com.mysting.tomato.redis.serializer.Serializer;
import com.mysting.tomato.redis.serializer.SerializerManager;

public class TestService {
	
	public static void main(String[] args) {
		Map map = new HashMap();
		
		for(int i = 0 ; i <=100 ; i++) {
			map.put("a"+i, "a"+i);
		}
		
		Serializer jdkSerializer =  SerializerManager.getSerializer(SerializerManager.JDK) ;
		Serializer kryoObjectSerializer =   SerializerManager.getSerializer(SerializerManager.KRYO) ;
		Serializer hessianSerializer =   SerializerManager.getSerializer(SerializerManager.HESSIAN2) ;
		long size = 0;  
        long time1 = System.currentTimeMillis();  
        
        byte[] jdkserialize = null ;
        byte[] redisserialize = null ;
        byte[] kryoserialize = null ;
        
        for (int i = 0; i < 1000000; i++) {  
            jdkserialize =jdkSerializer.serialize(map);  
            size += jdkserialize.length;  
        }  
        System.out.println("原生序列化方案[序列化100000次]耗时："  
                + (System.currentTimeMillis() - time1) + "ms size:=" + size);  
        
        
        long time2 = System.currentTimeMillis();  
        
      
        
        for (int i = 0; i < 1000000; i++) {  
            Map aa =(Map) jdkSerializer.deserialize(jdkserialize)  ;
        }  
        System.out.println("原生序列化方案[反序列化100000次]耗时："  
                + (System.currentTimeMillis() - time2) + "ms size:=" + size);  
        
        
        long time3 = System.currentTimeMillis();  
        size = 0;   
        
        for (int i = 0; i < 1000000; i++) {  
            redisserialize =hessianSerializer.serialize(map);  
            size += redisserialize.length;  
        }  
        System.out.println("hessian序列化方案[序列化100000次]耗时："  
                + (System.currentTimeMillis() - time3) + "ms size:=" + size);  
        
        
        long time4 = System.currentTimeMillis();  
        
      
        
        for (int i = 0; i < 1000000; i++) {  
            Map aa =(Map) hessianSerializer.deserialize(redisserialize)  ;
        }  
        System.out.println("hessian序列化方案[反序列化100000次]耗时："  
                + (System.currentTimeMillis() - time4) + "ms size:=" + size);  
        
        
        
        long time5 = System.currentTimeMillis();  
        size = 0;   
        
        for (int i = 0; i < 1000000; i++) {  
        	kryoserialize =kryoObjectSerializer.serialize(map);  
            size += kryoserialize.length;  
        }  
        System.out.println("Kryo序列化方案[序列化100000次]耗时："  
                + (System.currentTimeMillis() - time5) + "ms size:=" + size);  
        
        
        long time6 = System.currentTimeMillis();  
        
      
        
        for (int i = 0; i < 1000000; i++) {  
            Map aa =(Map) kryoObjectSerializer.deserialize(kryoserialize)  ;
        }  
        System.out.println("Kryo序列化方案[反序列化100000次]耗时："  
                + (System.currentTimeMillis() - time6) + "ms size:=" + size);  
        
        
	}

}
