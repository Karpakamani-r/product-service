package com.w2c.products.config.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public <T> ResponseEntity<APIResponse<T>> onSuccess(T data) {
        return new ResponseEntity<>(new APIResponse<T>(HttpStatus.OK.value(), "Success", data), HttpStatus.OK);
    }

    public <T> ResponseEntity<APIResponse<T>> onError(String message, HttpStatus status) {
        return new ResponseEntity<>(new APIResponse<T>(status.value(), message, null), status);
    }
}
