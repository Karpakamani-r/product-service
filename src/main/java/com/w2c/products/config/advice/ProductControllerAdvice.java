package com.w2c.products.config.advice;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.config.exceptions.UserNotFoundException;
import com.w2c.products.config.response.APIResponse;
import com.w2c.products.config.response.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice {

    @Autowired
    private ResponseService service;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> provideUserNotFoundException(UserNotFoundException e) {
        return service.onError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<APIResponse<Object>> provideUnknownException(UnknownException e) {
        return service.onError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<APIResponse<Object>> provideProductNotFoundException(ProductNotFoundException e) {
        return service.onError(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
