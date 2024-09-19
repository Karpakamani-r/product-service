package com.w2c.products.services;

import com.w2c.products.config.exceptions.ProductNotFoundException;
import com.w2c.products.config.exceptions.UnknownException;
import com.w2c.products.dto.ProductRequestDto;
import com.w2c.products.dto.ProductResponseDto;
import com.w2c.products.model.Product;
import com.w2c.products.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.w2c.products.util.Constants.QUALIFIER_DB_PRODUCTS;
import static com.w2c.products.util.ModelMapper.getProductFromResponse;
import static com.w2c.products.util.ModelMapper.getProductResponseFromProduct;

@Slf4j
@Service(value = QUALIFIER_DB_PRODUCTS)
public class ProductServiceDBImpl implements ProductService {

    private final ProductsRepository repository;
    private final ProductServiceRemoteImpl productServiceRemote;

    public ProductServiceDBImpl(ProductsRepository repository, ProductServiceRemoteImpl productServiceRemote) {
        this.repository = repository;
        this.productServiceRemote = productServiceRemote;
    }

    @Override
    public ProductResponseDto getProductById(long id) {
        if (id <= 0) throw new ProductNotFoundException("Product id is invalid, it should be greater than 0");
        log.info("Checking the product availability in database");
        Optional<Product> product = repository.getProductById(id);
        log.info("Is product found in db : " + product.isPresent());
        if (product.isEmpty()) {
            ProductResponseDto productDto = productServiceRemote.getProductById(id);
            if (productDto != null) {
                Product p = getProductFromResponse(productDto);
                repository.save(p);
                log.info("Product is exist in remote and successfully saved in db");
            }
            return productDto;
        } else {
            return getProductResponseFromProduct(product.get());
        }
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        log.info("Getting all products");
        return repository.findAll().stream().map(product -> ProductResponseDto.builder().productId(product.getId()).productName(product.getName()).image(product.getImage()).price(product.getPrice()).category(product.getCategory()).description(product.getDescription()).build()).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> insertNewProduct(ProductRequestDto request) {
        log.info("Trying to insert product...");
        Optional<Product> product = repository.getProductByNameAndCategory(request.getTitle(), request.getCategory());
        if (product.isPresent()) {
            throw new ProductNotFoundException("This product is already exist. title=" + request.getTitle() + " and category=" + request.getCategory());
        }
        Product p = repository.save(getProductFromRequest(request));
        if (p.getId() > 0) {
            throw new UnknownException("Unable to add this product. title=" + request.getTitle() + " and category=" + request.getCategory());
        }
        return product;
    }

    @Override
    public boolean deleteProduct(long id) {
        boolean deleted = false;
        log.info("Trying to delete product... id:" + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Successfully deleted");
            deleted = true;
        } else {
            log.info("Unable to delete - Product is not found");
        }
        return deleted;
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
