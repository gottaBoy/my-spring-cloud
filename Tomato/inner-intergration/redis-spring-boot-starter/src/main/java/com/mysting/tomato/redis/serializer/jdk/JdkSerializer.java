package com.mysting.tomato.redis.serializer.jdk;



import com.mysting.tomato.redis.serializer.Serializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Serializer for serialize and deserialize.
 * 
 * @author jiangping
 * @version $Id: Serializer.java, v 0.1 2015-10-4 PM9:37:57 tao Exp $
 */
public class JdkSerializer implements Serializer {

    private JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

   
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        
        return jdkSerializationRedisSerializer.serialize(obj);
    }
   
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data ) throws SerializationException {
         
        return (T) jdkSerializationRedisSerializer.deserialize(data);
    }

}
