package com.w2c.products.util;

import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import org.springframework.stereotype.Controller;

@Controller
public class ModelMapper {

    public ProductResponseDto getProductResponseFromProduct(Product product) {
        return ProductResponseDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    public Product getProductFromResponse(ProductResponseDto productDto) {
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

    public Product getProductFromRequest(ProductRequestDto request) {
        Product product = new Product();
        product.setName(request.getTitle());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        return product;
    }

    public ProductResponseDto getProductResponseFromDto(ProductDto productDto) {
        return ProductResponseDto.builder().productId(productDto.getId()).productName(productDto.getTitle()).price(productDto.getPrice()).image(productDto.getImage()).category(productDto.getCategory()).description(productDto.getDescription()).build();
    }
}
