package com.w2c.products.config.advice;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.config.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> provideUserNotFoundException(UserNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler(UnknownException.class)
    public ResponseEntity<String> provideUnknownException(UnknownException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(500));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> provideProductNotFoundException(ProductNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }
}
