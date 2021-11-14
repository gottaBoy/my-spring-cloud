package com.mysting.tomato.message.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;  

public interface Source {
    String OUTPUT = "output";


    @Output(Source.OUTPUT)
    MessageChannel output();

}
