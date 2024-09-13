package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.BaseResponse;
import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.w2c.products.util.Constants.*;

@Service(value = QUALIFIER_REMOTE_PRODUCTS)
public class ProductServiceRemoteImpl implements ProductService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductResponseDto getProductById(long id) {
        ProductDto productDto;
        try {
            productDto = restTemplate.getForObject(ENDPOINT_PRODUCTS + id, ProductDto.class);
        } catch (Exception e) {
            throw new UnknownException("Unknown error is occurred");
        }
        if (productDto == null) throw new ProductNotFoundException("The product is not found for given id " + id);
        return getProductResponseFromDto(productDto);
    }

    private ProductResponseDto getProductResponseFromDto(ProductDto productDto) {
        return ProductResponseDto.builder()
                .productId(productDto.getId())
                .productName(productDto.getTitle())
                .price(productDto.getPrice())
                .image(productDto.getImage())
                .category(productDto.getCategory())
                .description(productDto.getDescription())
                .build();
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return null;
    }

    @Override
    public BaseResponse insertNewProduct(ProductRequestDto product) {
        return null;
    }
}
