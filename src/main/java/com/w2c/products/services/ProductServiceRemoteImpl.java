package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.util.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.w2c.products.util.Constants.ENDPOINT_PRODUCTS;
import static com.w2c.products.util.Constants.QUALIFIER_REMOTE_PRODUCTS;

@Slf4j
@Service(value = QUALIFIER_REMOTE_PRODUCTS)
public class ProductServiceRemoteImpl implements ProductService {
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    ProductServiceRemoteImpl(RestTemplate restTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto getProductById(long id) {
        ProductDto productDto;
        try {
            log.info("Trying to get the product from remote.. id {}", id);
            productDto = restTemplate.getForObject(ENDPOINT_PRODUCTS + id, ProductDto.class);
        } catch (Exception e) {
            log.error("Exception is occurred while trying to get the product from remote. id {}", id, e);
            throw new UnknownException("Failed to retrieve product with id: " + id + "Exception: " + e.getMessage(), e);
        }
        if (productDto == null) {
            log.info("The product is not found in remote for given id {}", id);
            throw new ProductNotFoundException("The product is not found for given id " + id);
        }
        return modelMapper.getProductResponseFromDto(productDto);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return null;
    }

    @Override
    public Optional<Product> insertNewProduct(ProductRequestDto product) {
        return Optional.empty();
    }

    @Override
    public boolean deleteProduct(long id) {
        return false;
    }
}
