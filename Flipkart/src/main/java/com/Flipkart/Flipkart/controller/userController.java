package com.Flipkart.Flipkart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Flipkart.Flipkart.model.Users;
import com.Flipkart.Flipkart.response.MessageResponse;
import com.Flipkart.Flipkart.service.userService;

@RestController
@RequestMapping("/api/users")
public class userController {

    @Autowired
    private userService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<MessageResponse> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createUser(@RequestBody Users user) {
        return userService.createUser(user);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable Long userId, @RequestBody Users updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
