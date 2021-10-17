package com.mysting.tomato.rabbitmq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailResponse {

    private boolean ifSuccess;

    private String errorCode;

    private String errMsg;
}
