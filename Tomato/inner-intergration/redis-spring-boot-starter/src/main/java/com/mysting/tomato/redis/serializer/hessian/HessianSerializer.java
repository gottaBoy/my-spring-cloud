package com.mysting.tomato.redis.serializer.hessian;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.data.redis.serializer.SerializationException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.mysting.tomato.redis.serializer.Serializer;

/**
 * Serializer for serialize and deserialize.
 * 
 * @author jiangping
 * @version $Id: Serializer.java, v 0.1 2015-10-4 PM9:37:57 tao Exp $
 */
public class HessianSerializer implements Serializer {

    private SerializerFactory serializerFactory = new SerializerFactory();

   
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.setSerializerFactory(serializerFactory);
        try {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new SerializationException("IOException occurred when Hessian serializer encode!", e);
        }finally {
        	try {
				output.close();
			} catch (IOException e) {
			}
		}

        return byteArray.toByteArray();
    }

   
    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data ) throws SerializationException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        input.setSerializerFactory(serializerFactory);
        Object resultObject = null ;
        try {
            resultObject = input.readObject();
        } catch (IOException e) {
            throw new SerializationException("IOException occurred when Hessian serializer decode!", e);
        }finally {
        	try {
        		input.close();
			} catch (IOException e) {
			}
		}
        return (T) resultObject;
    }

}
