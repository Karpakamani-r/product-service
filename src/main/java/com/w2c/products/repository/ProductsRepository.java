package com.w2c.products.repository;

import com.w2c.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(long id);
    Optional<Product> getProductByNameAndCategory(String name, String category);
}
