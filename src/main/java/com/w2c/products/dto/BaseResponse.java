package com.w2c.products.dto;

import lombok.Data;

@Data
public class BaseResponse {
    private int statusCode;
    private String message;
}
