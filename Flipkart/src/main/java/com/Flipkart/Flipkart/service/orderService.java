package com.Flipkart.Flipkart.service;

import org.springframework.http.ResponseEntity;

import com.Flipkart.Flipkart.response.MessageResponse;

public interface orderService {
	
    public ResponseEntity<MessageResponse> placeOrder(Long userId) ;
    
    public ResponseEntity<MessageResponse> getOrderedProductsByUser(Long userId);
    


}
