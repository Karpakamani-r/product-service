package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductAlreadyExistException;
import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.repository.ProductsRepository;
import com.w2c.products.util.ModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.w2c.products.util.Constants.QUALIFIER_DB_PRODUCTS;


@Slf4j
@Service(value = QUALIFIER_DB_PRODUCTS)
public class ProductServiceDBImpl implements ProductService {

    private final ProductsRepository repository;
    private final ProductServiceRemoteImpl productServiceRemote;
    private final ModelMapper modelMapper;

    public ProductServiceDBImpl(ProductsRepository repository, ProductServiceRemoteImpl productServiceRemote, ModelMapper modelMapper) {
        this.repository = repository;
        this.productServiceRemote = productServiceRemote;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDto getProductById(long id) {
        log.info("Checking the product availability in database");
        Optional<Product> product = repository.getProductById(id);
        log.info("Is product found in db : " + product.isPresent());
        if (product.isEmpty()) {
            ProductResponseDto productDto = productServiceRemote.getProductById(id);
            if (productDto != null) {
                Product p = modelMapper.getProductFromResponse(productDto);
                repository.save(p);
                log.info("Product is exist in remote and successfully saved in db");
            }
            return productDto;
        } else {
            return modelMapper.getProductResponseFromProduct(product.get());
        }
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        log.info("Getting all products");
        return repository.findAll().stream().map(product -> ProductResponseDto.builder().productId(product.getId()).productName(product.getName()).image(product.getImage()).price(product.getPrice()).category(product.getCategory()).description(product.getDescription()).build()).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> insertNewProduct(ProductRequestDto request) {
        log.info("Trying to insert product... Title {}, Category {}", request.getTitle(), request.getCategory());

        Optional<Product> product = repository.getProductByNameAndCategory(request.getTitle(), request.getCategory());
        if (product.isPresent()) {
            throw new ProductAlreadyExistException("Product already exists. Title: " + request.getTitle() + ", Category: " + request.getCategory());
        }

        try {
            return Optional.of(repository.save(modelMapper.getProductFromRequest(request)));
        } catch (Exception e) {
            log.error("Exception while inserting product. Title {}, Category {}", request.getTitle(), request.getCategory(), e);
            throw new UnknownException("Unable to add product. Title: " + request.getTitle() + ", Category: " + request.getCategory(), e.getCause());
        }
    }

    @Override
    public boolean deleteProduct(long id) {
        log.info("Trying to delete product... id {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Successfully deleted. id {}", id);
        } else {
            log.error("Unable to delete. Product not found with id {}", id);
            throw new ProductNotFoundException("Unable to delete, product is not found. ProductId: " + id);
        }
        return true;
    }
}
