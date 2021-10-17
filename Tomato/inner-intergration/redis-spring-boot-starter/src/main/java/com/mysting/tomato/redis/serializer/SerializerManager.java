package com.mysting.tomato.redis.serializer;

import com.mysting.tomato.redis.serializer.hessian.HessianSerializer;
import com.mysting.tomato.redis.serializer.jdk.JdkSerializer;
import com.mysting.tomato.redis.serializer.kryo.KryoSerializer;

/**
 * Serializer for serialize and deserialize.
 * 
 * @author jiangping
 * @version $Id: Serializer.java, v 0.1 2015-10-4 PM9:37:57 tao Exp $
 */
public class SerializerManager {

    private static Serializer[] serializers = new Serializer[5];
    
    public static final byte    JDK        =  0;
    public static final byte    KRYO        = 1;
    public static final byte    HESSIAN2    = 2;
    //public static final byte    Json        = 3;

    static {
    	addSerializer(JDK, new JdkSerializer());
    	addSerializer(KRYO, new KryoSerializer());
        addSerializer(HESSIAN2, new HessianSerializer());
    }

    public static Serializer getSerializer(int idx) {
        return serializers[idx];
    }

    public static void addSerializer(int idx, Serializer serializer) {
        if (serializers.length <= idx) {
            Serializer[] newSerializers = new Serializer[idx + 5];
            System.arraycopy(serializers, 0, newSerializers, 0, serializers.length);
            serializers = newSerializers;
        }
        serializers[idx] = serializer;
    }
}
