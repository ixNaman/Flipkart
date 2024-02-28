package com.Flipkart.Flipkart.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Flipkart.Flipkart.model.Product;
import com.Flipkart.Flipkart.response.MessageResponse;

public interface productService {
	
	ResponseEntity<?> getAllProducts();

    ResponseEntity<MessageResponse> getProductById(Long productId);

    ResponseEntity<MessageResponse> createProduct(Product product);

    ResponseEntity<MessageResponse> updateProduct(Long productId, Product updatedProduct);


}
