package com.w2c.products.dto;

import lombok.Data;

@Data
public class ProductResponseDto extends BaseResponse {
    private long productId;
    private String productName;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDto rating;
}
