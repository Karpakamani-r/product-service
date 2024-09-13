package com.w2c.products.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private long productId;
    private String productName;
    private Double price;
    private String description;
    private String category;
    private String image;
    private RatingDto rating;
}
