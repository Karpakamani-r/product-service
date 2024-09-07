package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.model.Rating;
import com.w2c.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

import static com.w2c.products.util.Constants.QUALIFIER_DB_PRODUCTS;
import static com.w2c.products.util.ModelMapper.getProductFromResponse;
import static com.w2c.products.util.ModelMapper.getProductResponseFromProduct;

@Service(value = QUALIFIER_DB_PRODUCTS)
public class ProductServiceDBImpl implements ProductService {
    @Autowired
    private ProductsRepository repository;

    @Autowired
    private ProductServiceRemoteImpl productServiceRemote;

    @Override
    public ProductResponseDto getProductById(long id) {
        System.out.println("Checking the product availability in database");
        Optional<Product> product = repository.getProductByProductId(id);
        System.out.println("Is product found in db : " + product.isPresent());
        if (product.isEmpty()) {
            System.out.println("Getting the product from remote");
            ProductResponseDto productDto = productServiceRemote.getProductById(id);
            if (productDto.getStatusCode() == 200) {
                Product p = getProductFromResponse(productDto);
                repository.save(p);
                System.out.println("Product is exist in remote and successfully saved in db");
            }
            return productDto;
        } else {
            return getProductResponseFromProduct(product.get());
        }
    }
}
