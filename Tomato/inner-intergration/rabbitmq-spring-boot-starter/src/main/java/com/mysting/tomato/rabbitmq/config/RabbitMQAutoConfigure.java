package com.mysting.tomato.rabbitmq.config;

import com.mysting.tomato.rabbitmq.comsumer.FastRabbitConsumerProcessor;
import com.mysting.tomato.rabbitmq.comsumer.FastBuildRabbitMqConsumer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysting.tomato.rabbitmq.producer.FastBuildRabbitMqProducer;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnClass({FastBuildRabbitMqProducer.class, FastBuildRabbitMqConsumer.class})
@EnableConfigurationProperties(RabbitMQProperties.class)
public class RabbitMQAutoConfigure {


    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    @PostConstruct
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(rabbitMQProperties.getAddresses());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitMQProperties.getVirtualHost());
        connectionFactory.setPublisherConfirms(rabbitMQProperties.isPublisherConfirms());
        return connectionFactory;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocp.fast.rabbitmq", value = "enalbe", havingValue = "true")
    public FastBuildRabbitMqProducer fastRabbitMQProducer(ConnectionFactory connectionFactory) {
        return new FastBuildRabbitMqProducer(connectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocp.fast.rabbitmq", value = "enalbe", havingValue = "true")
    public FastBuildRabbitMqConsumer fastBuildRabbitMqConsumer(ConnectionFactory connectionFactory) {
        return new FastBuildRabbitMqConsumer(connectionFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "ocp.fast.rabbitmq", value = "enalbe", havingValue = "true")
    public FastRabbitConsumerProcessor fastRabbitConsumerProcessor() {
        return new FastRabbitConsumerProcessor();
    }
}
