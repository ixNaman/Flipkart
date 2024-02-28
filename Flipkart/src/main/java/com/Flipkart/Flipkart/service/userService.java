package com.Flipkart.Flipkart.service;

import org.springframework.http.ResponseEntity;

import com.Flipkart.Flipkart.model.Users;
import com.Flipkart.Flipkart.response.MessageResponse;

public interface userService {
	
	 	ResponseEntity<?> getAllUsers();

	    ResponseEntity<MessageResponse> getUserById(Long userId);

	    ResponseEntity<MessageResponse> createUser(Users user);

	    ResponseEntity<MessageResponse> updateUser(Long userId, Users updatedUser);

	    ResponseEntity<MessageResponse> deleteUser(Long userId);
}
