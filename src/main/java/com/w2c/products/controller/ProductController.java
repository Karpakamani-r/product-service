package com.w2c.products.controller;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.dto.BaseResponse;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.services.ProductServiceDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<ProductResponseDto> response = productService.getAllProducts();
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/")
    @ResponseBody
    ResponseEntity<BaseResponse> addNewProduct(@RequestBody ProductRequestDto request) {
        checkBasicValidation(request);
        BaseResponse response = productService.insertNewProduct(request);
        if (response == null) {
            response = new BaseResponse();
            response.setMessage("Invalid request, Unable to insert this product");
            response.setStatusCode(400);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    private void checkBasicValidation(ProductRequestDto request) {
        String productName = request.getTitle();
        String category = request.getCategory();
        if (productName == null || productName.trim().isEmpty())
            throw new ProductNotFoundException("Product name is invalid");
        if (category == null || category.trim().isEmpty())
            throw new ProductNotFoundException("Product name is invalid");
    }
}
