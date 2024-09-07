package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.w2c.products.util.Constants.*;

@Service(value = QUALIFIER_REMOTE_PRODUCTS)
public class ProductServiceRemoteImpl implements ProductService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ProductResponseDto getProductById(long id) {
        ProductDto productDto;
        try {
            productDto = restTemplate.getForObject(BASE_URL + ENDPOINT_PRODUCTS + id, ProductDto.class);
        } catch (Exception e) {
            throw new UnknownException("Unknown error is occurred");
        }
        if (productDto == null) throw new ProductNotFoundException("The product is not found for given id " + id);
        return getProductResponseFromDto(productDto);
    }

    private ProductResponseDto getProductResponseFromDto(ProductDto productDto) {
        ProductResponseDto response = new ProductResponseDto();
        response.setProductId(productDto.getId());
        response.setProductName(productDto.getTitle());
        response.setPrice(productDto.getPrice());
        response.setImage(productDto.getImage());
        response.setCategory(productDto.getCategory());
        response.setDescription(productDto.getDescription());
        response.setStatusCode(200);
        response.setMessage("Success");
        return response;
    }
}
