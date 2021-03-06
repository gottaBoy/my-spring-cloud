package com.mysting.tomato.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity<T> implements Serializable {


    private static final long serialVersionUID = -4696008537295855861L;
    private T data;
    private Integer code;
    private String msg;

    public static <T> ResponseEntity<T> succeed(String msg) {
        return succeedWith(null, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseEntity<T> succeed(T model, String msg) {
        return succeedWith(model, CodeEnum.SUCCESS.getCode(), msg);
    }

    public static <T> ResponseEntity<T> succeedWith(T data, Integer code, String msg) {
        return new ResponseEntity<T>(data, code, msg);
    }

    public static <T> ResponseEntity<T> failed(String msg) {
        return failedWith(null, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> ResponseEntity<T> failed(T model, String msg) {
        return failedWith(model, CodeEnum.ERROR.getCode(), msg);
    }

    public static <T> ResponseEntity<T> failedWith(T data, Integer code, String msg) {
        return new ResponseEntity<T>(data, code, msg);
    }

}
