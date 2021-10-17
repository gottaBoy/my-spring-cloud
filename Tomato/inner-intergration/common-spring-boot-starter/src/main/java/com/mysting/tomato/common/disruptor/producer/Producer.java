package com.mysting.tomato.common.disruptor.producer;


import java.util.Map;

import com.lmax.disruptor.RingBuffer;
import com.mysting.tomato.common.disruptor.message.DataWrapper;

public class Producer <ENTITY extends DataWrapper>  {

    private RingBuffer<DataWrapper> ringBuffer;

    public Producer(RingBuffer<DataWrapper> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    public void sendData( Map dataMap) {
        long sequnce = ringBuffer.next();
        try {
        	DataWrapper dataInfo = ringBuffer.get(sequnce);
        	dataInfo.setDataMap(dataMap);
        } finally {
            ringBuffer.publish(sequnce);
        }
    }
    
    
    
    
}
