package com.mysting.tomato.rabbitmq.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mysting.tomato.rabbitmq.common.DetailResponse;
import com.mysting.tomato.rabbitmq.common.FastOcpRabbitMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import com.mysting.tomato.rabbitmq.cache.RetryCache;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FastBuildRabbitMqProducer {
    @Autowired
    private AmqpAdmin amqpAdmin;
    private ConnectionFactory connectionFactory;

    public FastBuildRabbitMqProducer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public MessageSender buildDirectMessageSender(final String exchange, final String routingKey, final String queue) throws IOException {
        return buildMessageSender(exchange, routingKey, queue, "direct");
    }

    public MessageSender buildTopicMessageSender(final String exchange, final String routingKey) throws IOException {
        return buildMessageSender(exchange, routingKey, null, "topic");
    }

    /**
     * 发送消息
     *
     * @param exchange   消息交换机
     * @param routingKey 消息路由key
     * @param queue      消息队列
     * @param type       消息类型
     *                   return
     */
    private MessageSender buildMessageSender(final String exchange, final String routingKey, final String queue,
                                             final String type) throws IOException {
        Connection connection = connectionFactory.createConnection();
        //1
        if (type.equals("direct")) {
            buildQueue(exchange, routingKey, queue, connection, "direct");
        } else if (type.equals("topic")) {
            buildTopic(exchange, connection);
        }

        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        RetryCache retryCache = new RetryCache();

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("send message failed: " + cause + correlationData.toString());
            } else {
                retryCache.del(Long.valueOf(correlationData.getId()));
            }
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, tmpExchange, tmpRoutingKey) -> {
            try {
                Thread.sleep(FastOcpRabbitMqConstants.ONE_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            log.info("send message failed: " + replyCode + " " + replyText);
            rabbitTemplate.send(message);
        });

        return new MessageSender() {
            {
                retryCache.setSender(this);
            }

            @Override
            public DetailResponse send(Object message) {
                long id = retryCache.generateId();
                long time = System.currentTimeMillis();

                return send(new MessageWithTime(id, time, message));
            }

            @Override
            public DetailResponse send(MessageWithTime messageWithTime) {
                try {
                    retryCache.add(messageWithTime);
                    rabbitTemplate.correlationConvertAndSend(messageWithTime.getMessage(),
                            new CorrelationData(String.valueOf(messageWithTime.getId())));
                } catch (Exception e) {
                    return new DetailResponse(false, "", "");
                }
                return new DetailResponse(true, "", "");
            }
        };
    }


    private void buildQueue(String exchange, String routingKey,
                            final String queue, Connection connection, String type) throws IOException {
        Channel channel = connection.createChannel(false);

        if (type.equals("direct")) {
            //exchange为空的direct交换机为DirectExchange.DEFAULT，mq自动创建，重复创建如果参数不一致会报错
            if (!"".equals(exchange)) {
                channel.exchangeDeclare(exchange, "direct", true, false, null);
            }
        } else if (type.equals("topic")) {
            channel.exchangeDeclare(exchange, "topic", true, false, null);
        }

        channel.queueDeclare(queue, true, false, false, null);
        channel.queueBind(queue, exchange, routingKey);
        try {
            channel.close();
            //调用频率较高时，connection自动关闭来不及，会导致连接超出上限
            connection.close();
        } catch (TimeoutException e) {
            log.info("close channel time out ", e);
        } finally {
            //关闭连接资源
        }
    }

    private void buildTopic(String exchange, Connection connection) throws IOException {
        Channel channel = connection.createChannel(false);
        channel.exchangeDeclare(exchange, "topic", true, false, null);
    }


}
