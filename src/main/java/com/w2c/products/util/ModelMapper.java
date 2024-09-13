package com.w2c.products.util;

import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;

public class ModelMapper {

    public static ProductResponseDto getProductResponseFromProduct(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    public static Product getProductFromResponse(ProductResponseDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
//        Rating rating = new Rating();
//        rating.setAverageRating(productDto.getRating().getRate());
//        rating.setRatingCount(productDto.getRating().getCount());
//        product.setRating(rating);
        return product;
    }
}
