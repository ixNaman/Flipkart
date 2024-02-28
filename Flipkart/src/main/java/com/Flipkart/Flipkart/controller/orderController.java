package com.Flipkart.Flipkart.controller;

import com.Flipkart.Flipkart.response.MessageResponse;
import com.Flipkart.Flipkart.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class orderController {

    @Autowired
    private orderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<MessageResponse> placeOrder(@RequestParam Long cartId) {
        return orderService.placeOrder(cartId);
    }
    
    @GetMapping("/user/{userId}/products")
    public ResponseEntity<MessageResponse> getOrderedProductsByUser(@PathVariable Long userId) {
		return orderService.getOrderedProductsByUser(userId);
	}
}
