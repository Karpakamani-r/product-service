package com.w2c.products.controller;

import com.w2c.products.config.response.APIResponse;
import com.w2c.products.config.response.ResponseService;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.services.ProductServiceDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductController {
    /*  @Qualifier(value = QUALIFIER_REMOTE_PRODUCTS)
        @Autowired
        private ProductService productService;*/
    @Autowired
    private ProductServiceDBImpl productService;

    @Autowired
    private ResponseService responseService;

    @GetMapping("/{productId}")
    @ResponseBody
    ResponseEntity<APIResponse<ProductResponseDto>> getProductById(@PathVariable("productId") long productId) {
        ProductResponseDto response = productService.getProductById(productId);
        return responseService.onSuccess(response);
    }

    @GetMapping("/")
    @ResponseBody
    ResponseEntity<APIResponse<List<ProductResponseDto>>> getProducts() {
        List<ProductResponseDto> response = productService.getAllProducts();
        return responseService.onSuccess(response);
    }

    @PostMapping("/")
    @ResponseBody
    ResponseEntity<APIResponse<Product>> addNewProduct(@RequestBody ProductRequestDto request) {
        checkBasicValidation(request);
        Optional<Product> response = productService.insertNewProduct(request);
        return responseService.onSuccess(response.get());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    ResponseEntity<APIResponse<Object>> deleteProduct(@PathVariable long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return responseService.onSuccess(null);
        } else {
            return responseService.onError("Unable to delete", HttpStatus.BAD_REQUEST);
        }
    }

    private void checkBasicValidation(ProductRequestDto request) {
        String productName = request.getTitle();
        String category = request.getCategory();
        if (productName == null || productName.trim().isEmpty()) {
            responseService.onError("Product name should not be null", HttpStatus.BAD_REQUEST);
        }
        if (category == null || category.trim().isEmpty()) {
            responseService.onError("Product category should not be null", HttpStatus.BAD_REQUEST);
        }
    }
}
