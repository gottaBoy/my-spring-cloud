package com.mysting.tomato.message.service;
 

import java.util.Map;

import com.mysting.tomato.message.config.Sink;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {
	@StreamListener(target = Sink.INPUT)
    public void handleGreetings(@Payload Map msg) {
        System.out.println("Received greetings: " +  msg);
    }
}
