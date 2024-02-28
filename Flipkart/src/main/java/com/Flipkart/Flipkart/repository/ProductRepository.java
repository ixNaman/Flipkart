package com.Flipkart.Flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Flipkart.Flipkart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
