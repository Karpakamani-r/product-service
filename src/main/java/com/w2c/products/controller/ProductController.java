package com.w2c.products.controller;

import com.w2c.products.dto.ProductDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.services.ProductService;
import com.w2c.products.services.ProductServiceDBImpl;
import com.w2c.products.services.ProductServiceRemoteImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController {
/*  @Qualifier(value = QUALIFIER_REMOTE_PRODUCTS)
    @Autowired
    private ProductService productService;*/
    @Autowired
    private ProductServiceDBImpl productService;

    @GetMapping("/{productId}")
    @ResponseBody
    ResponseEntity<ProductResponseDto> getProductById(@PathVariable("productId") long productId) {
        ProductResponseDto response = productService.getProductById(productId);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/")
    @ResponseBody
    ResponseEntity<ProductDto> getProducts() {
        return null;
    }
}
