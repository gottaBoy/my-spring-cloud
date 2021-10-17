package com.mysting.tomato.redis.serializer;

import org.springframework.data.redis.serializer.SerializationException;

/**
 * Serializer for serialize and deserialize.
 * 
 * @author jiangping
 * @version $Id: Serializer.java, v 0.1 2015-10-4 PM9:37:57 tao Exp $
 */
public interface Serializer {
    /**
     * Encode object into bytes.
     * 
     * @param obj target object
     * @return serialized result
     */
    byte[] serialize(final Object obj) throws SerializationException;

    /**
     * Decode bytes into Object.
     * 
     * @param data serialized data
     * @param classOfT class of original data
     */
    <T> T deserialize(final byte[] data ) throws SerializationException;
}
