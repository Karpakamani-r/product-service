package com.w2c.products.services;

import com.w2c.products.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto getProductById(long id);
}
