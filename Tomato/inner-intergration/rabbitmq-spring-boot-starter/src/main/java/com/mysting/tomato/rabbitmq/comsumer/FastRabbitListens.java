package com.mysting.tomato.rabbitmq.comsumer;


import com.mysting.tomato.rabbitmq.annotation.FastRabbitListener;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastRabbitListens {
    FastRabbitListener[] value();
}
