package com.mysting.tomato.redis.serializer.kryo;



import java.io.ByteArrayOutputStream;

import com.mysting.tomato.redis.serializer.Serializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Serializer for serialize and deserialize.
 * 
 * @author jiangping
 * @version $Id: Serializer.java, v 0.1 2015-10-4 PM9:37:57 tao Exp $
 */
public class KryoSerializer implements Serializer {

	private  CKryoPool kp = new CKryoPool();

   
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
    	Kryo kryo = kp.getPool().borrow();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Output output = new Output(os);
            kryo.writeClassAndObject(output, obj);
            output.close();
            return os.toByteArray();
        } finally {
        	kp.getPool().release(kryo);
        } 
    }
   
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data ) throws SerializationException {
    	 Kryo kryo = kp.getPool().borrow();
         try {
             Object obj = kryo.readClassAndObject(new Input(data));
                 return (T) obj;
         } catch (Exception ex) {
             throw new SerializationException("class not found.", ex);
         } finally {
        	 kp.getPool().release(kryo);
         } 
    }

}
