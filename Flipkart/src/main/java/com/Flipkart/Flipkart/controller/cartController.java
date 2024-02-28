package com.Flipkart.Flipkart.controller;

import com.Flipkart.Flipkart.response.MessageResponse;
import com.Flipkart.Flipkart.service.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class cartController {

    @Autowired
    private cartService cartService;

    @PostMapping("/addProduct/{userId}/{productId}")
    public ResponseEntity<MessageResponse> addProductToCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        return cartService.addProductToCart(userId, productId);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<MessageResponse> getCartById(@PathVariable Long cartId) {
        return cartService.getCartById(cartId);
    }

    @GetMapping("/getAllCarts")
    public ResponseEntity<?> getAllCarts() {
        return cartService.getAllCarts();
    }
}
