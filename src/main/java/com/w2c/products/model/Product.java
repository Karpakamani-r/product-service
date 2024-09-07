package com.w2c.products.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private String name;
    @Column(length = 2000)
    private String description;
    private Double price;
    private String category;
    private String image;
//    @OneToMany
//    private Rating rating;
}
