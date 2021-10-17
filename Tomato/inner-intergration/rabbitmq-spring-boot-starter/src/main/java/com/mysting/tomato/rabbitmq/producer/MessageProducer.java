package com.mysting.tomato.rabbitmq.producer;

import com.mysting.tomato.rabbitmq.common.DetailResponse;

public interface MessageProducer {


    DetailResponse send(Object message);

}