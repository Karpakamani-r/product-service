package com.w2c.products.services;

import com.w2c.products.dto.BaseResponse;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getProductById(long id);

    List<ProductResponseDto> getAllProducts();

    BaseResponse insertNewProduct(ProductRequestDto product);
}
