package com.mysting.tomato.common.serializer;

import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

import com.mysting.tomato.redis.serializer.SerializerManager;

public class DefaultSerializationStrategy extends StandardStringSerializationStrategy {


	@Override
	@SuppressWarnings("unchecked")
	protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
		return (T) SerializerManager.getSerializer(SerializerManager.HESSIAN2).deserialize(bytes);
	}

	@Override
	protected byte[] serializeInternal(Object object) {
		return SerializerManager.getSerializer(SerializerManager.HESSIAN2).serialize(object);
	}
}