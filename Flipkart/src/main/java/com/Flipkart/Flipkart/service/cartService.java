package com.Flipkart.Flipkart.service;

import org.springframework.http.ResponseEntity;

import com.Flipkart.Flipkart.response.MessageResponse;

public interface cartService {
	
	ResponseEntity<?> getAllCarts();

    ResponseEntity<MessageResponse> getCartById(Long cartId);


    ResponseEntity<MessageResponse> addProductToCart(Long userId, Long productId);


}
