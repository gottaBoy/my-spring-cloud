package com.mysting.tomato.rabbitmq.comsumer;



import com.mysting.tomato.rabbitmq.common.DetailResponse;


public interface MessageProcess<T> {
    DetailResponse process(T message);
}
