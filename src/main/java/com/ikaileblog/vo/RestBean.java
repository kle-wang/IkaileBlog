package com.ikaileblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestBean<T> {
    int code;
    String reason;
    T data;

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
