package com.mysting.tomato.redis.serializer.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

public class CKryoPool {
    private KryoFactory factory = () -> {
        Kryo kryo = new Kryo();
        return kryo;
    };

    private final KryoPool pool = new KryoPool.Builder(factory).softReferences().build();

    public KryoPool getPool() {
        return pool;
    }
} 
