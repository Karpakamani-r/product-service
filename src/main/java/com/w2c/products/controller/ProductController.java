package com.w2c.products.controller;

import com.w2c.products.config.response.APIResponse;
import com.w2c.products.config.response.ResponseHandler;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.services.ProductService;
import com.w2c.products.util.BasicProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.w2c.products.util.Constants.QUALIFIER_DB_PRODUCTS;

@RestController
@RequestMapping("products")
public class ProductController {
    @Qualifier(value = QUALIFIER_DB_PRODUCTS)
    @Autowired
    private ProductService productService;
    @Autowired
    private ResponseHandler responseHandler;
    @Autowired
    private BasicProductValidator validator;

    @GetMapping("/{productId}")
    ResponseEntity<APIResponse<ProductResponseDto>> getProductById(@PathVariable("productId") long productId) {
        validator.validateProductId(productId);
        ProductResponseDto response = productService.getProductById(productId);
        return responseHandler.onSuccess(response);
    }

    @GetMapping("/")
    ResponseEntity<APIResponse<List<ProductResponseDto>>> getProducts() {
        List<ProductResponseDto> response = productService.getAllProducts();
        return responseHandler.onSuccess(response);
    }

    @PostMapping("/")
    ResponseEntity<APIResponse<Product>> addNewProduct(@RequestBody ProductRequestDto request) {
        validator.validateProduct(request);
        Optional<Product> response = productService.insertNewProduct(request);
        return response.map(product -> responseHandler.onSuccess(product)).orElseGet(() -> responseHandler.onError("Unable to add product", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<APIResponse<Object>> deleteProduct(@PathVariable long id) {
        validator.validateProductId(id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return responseHandler.onSuccess(null);
        } else {
            return responseHandler.onError("Unable to delete product", HttpStatus.BAD_REQUEST);
        }
    }
}
