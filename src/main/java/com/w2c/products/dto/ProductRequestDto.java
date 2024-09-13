package com.w2c.products.dto;

import lombok.Data;

@Data
public class ProductRequestDto {
    String title;
    Double price;
    String description;
    String category;
    String image;
}
