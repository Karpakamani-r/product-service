package com.w2c.products.config.response;

import lombok.Data;

@Data
public class APIResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public APIResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}
