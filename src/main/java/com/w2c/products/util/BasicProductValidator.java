package com.w2c.products.util;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.response.ResponseHandler;
import com.w2c.products.dto.ProductRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BasicProductValidator {
    @Autowired
    private ResponseHandler responseHandler;

    public void validateProductId(long id) {
        if (id <= 0) throw new ProductNotFoundException("Product id is invalid, it should be greater than 0");
    }

    public void validateProduct(ProductRequestDto request) {
        String productName = request.getTitle();
        String category = request.getCategory();
        if (productName == null || productName.trim().isEmpty()) {
            responseHandler.onError("Product name should not be null", HttpStatus.BAD_REQUEST);
        }
        if (category == null || category.trim().isEmpty()) {
            responseHandler.onError("Product category should not be null", HttpStatus.BAD_REQUEST);
        }
    }
}
