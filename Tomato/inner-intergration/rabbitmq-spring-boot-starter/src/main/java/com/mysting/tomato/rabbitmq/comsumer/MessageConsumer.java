package com.mysting.tomato.rabbitmq.comsumer;


import com.mysting.tomato.rabbitmq.common.DetailResponse;

public interface MessageConsumer {
    DetailResponse consume();
}
