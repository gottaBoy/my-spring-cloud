package com.mysting.tomato.rabbitmq.annotation;


import com.mysting.tomato.rabbitmq.comsumer.FastRabbitListens;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * rabbitmq消息消费者监听，无需声明队列及建立绑定，可以直接接收队列消息
 * https://www.cnblogs.com/ZhuChangwu/p/14093107.html#rabbitmq%E4%B8%AD%E7%9A%84%E6%A6%82%E5%BF%B5
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Repeatable(FastRabbitListens.class)
public @interface FastRabbitListener {
    /**
     * 交换机名称
     * @return
     */
    String exchange() default "";

    /**
     * 路由健
     * @return
     */
    String routingKey();

    /**
     * 队列名
     * @return
     */
    String queue();

    /**
     * 类型
     * @return
     */
    String type();

    /**
     * 消费线程数
     *
     * @return
     */
    String concurrency() default "1";
}
