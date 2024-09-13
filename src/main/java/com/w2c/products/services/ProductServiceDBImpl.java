package com.w2c.products.services;

import com.w2c.products.dto.BaseResponse;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            if (productDto != null) {
                Product p = getProductFromResponse(productDto);
                repository.save(p);
                System.out.println("Product is exist in remote and successfully saved in db");
            }
            return productDto;
        } else {
            return getProductResponseFromProduct(product.get());
        }
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return repository.findAll().stream().map(product ->
                ProductResponseDto.builder()
                        .productId(product.getProductId())
                        .productName(product.getName())
                        .image(product.getImage())
                        .price(product.getPrice())
                        .category(product.getCategory())
                        .description(product.getDescription()).build()
        ).collect(Collectors.toList());
    }

    @Override
    public BaseResponse insertNewProduct(ProductRequestDto request) {
        Product p = repository.save(getProductFromRequest(request));
        if (p.getId() > 0) {
            BaseResponse response = new BaseResponse();
            response.setStatusCode(201);
            response.setMessage("Success");
            return response;
        }
        return null;
    }

    private Product getProductFromRequest(ProductRequestDto request) {
        Product product = new Product();
        product.setName(request.getTitle());
        product.setImage(request.getImage());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        return product;
    }
}
