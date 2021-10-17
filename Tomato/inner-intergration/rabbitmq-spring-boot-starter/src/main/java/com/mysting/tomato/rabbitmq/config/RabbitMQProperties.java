package com.mysting.tomato.rabbitmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mysting.fast.rabbitmq")
public class RabbitMQProperties {

    private boolean enable;

    private String addresses;

    private String username;

    private String password;

    private String virtualHost;

    private boolean publisherConfirms = true;
}
