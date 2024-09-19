package com.w2c.products.services;

import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponseDto getProductById(long id);

    List<ProductResponseDto> getAllProducts();

    Optional<Product> insertNewProduct(ProductRequestDto product);

    boolean deleteProduct(long id);
}
