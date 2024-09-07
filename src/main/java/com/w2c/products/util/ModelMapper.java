package com.w2c.products.util;

import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.model.Rating;

public class ModelMapper {

    public static ProductResponseDto getProductResponseFromProduct(Product product) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setProductId(product.getProductId());
        responseDto.setProductName(product.getName());
        responseDto.setDescription(product.getDescription());
        responseDto.setMessage("Success");
        responseDto.setStatusCode(200);
        responseDto.setCategory(product.getCategory());
        responseDto.setPrice(product.getPrice());
        responseDto.setImage(product.getImage());
        return responseDto;
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
