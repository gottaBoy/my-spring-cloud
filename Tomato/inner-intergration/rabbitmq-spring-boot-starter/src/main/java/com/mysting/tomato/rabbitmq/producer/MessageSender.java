package com.mysting.tomato.rabbitmq.producer;

import com.mysting.tomato.rabbitmq.common.DetailResponse;

public interface MessageSender {


    DetailResponse send(Object message);

    DetailResponse send(MessageWithTime messageWithTime);
}