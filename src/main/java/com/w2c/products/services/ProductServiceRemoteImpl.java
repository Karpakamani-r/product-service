package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.w2c.products.util.Constants.*;

@Slf4j
@Service(value = QUALIFIER_REMOTE_PRODUCTS)
public class ProductServiceRemoteImpl implements ProductService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceRemoteImpl.class);

    ProductServiceRemoteImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getProductById(long id) {
        ProductDto productDto;
        try {
            log.info("Trying to get the product from remote..");
            productDto = restTemplate.getForObject(ENDPOINT_PRODUCTS + id, ProductDto.class);
        } catch (Exception e) {
            throw new UnknownException("Unknown error is occurred : " + e.getMessage());
        }
        if (productDto == null) throw new ProductNotFoundException("The product is not found for given id " + id);
        return getProductResponseFromDto(productDto);
    }

    private ProductResponseDto getProductResponseFromDto(ProductDto productDto) {
        return ProductResponseDto.builder().productId(productDto.getId()).productName(productDto.getTitle()).price(productDto.getPrice()).image(productDto.getImage()).category(productDto.getCategory()).description(productDto.getDescription()).build();
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return null;
    }

    @Override
    public Optional<Product> insertNewProduct(ProductRequestDto product) {
        return null;
    }

    @Override
    public boolean deleteProduct(long id) {
        return false;
    }
}
