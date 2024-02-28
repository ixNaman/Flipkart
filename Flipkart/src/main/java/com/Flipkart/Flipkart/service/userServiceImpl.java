package com.Flipkart.Flipkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Flipkart.Flipkart.model.Cart;
import com.Flipkart.Flipkart.model.Users;
import com.Flipkart.Flipkart.repository.CartRepository;
import com.Flipkart.Flipkart.repository.UserRepository;
import com.Flipkart.Flipkart.response.MessageResponse;

@Service
public class userServiceImpl implements userService {
	
	@Autowired
	private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getAllUsers() {
    	try {
            List<Users> users = userRepository.findAll();
            if (users.isEmpty()) {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "User not found"), HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok(new MessageResponse(users , "Successfully fetched all users"));
            }
        } catch (Exception e) {
            
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<MessageResponse> getUserById(Long userId) {
        try {
            Optional<Users> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                Users user = optionalUser.get();
                return ResponseEntity.ok(new MessageResponse(user, "Success"));
            } else {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "User not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Override
    public ResponseEntity<MessageResponse> createUser(Users user) {
        try {
            // Check if the user already has a cart
            if (user.getCart() == null) {
                // If not, create a new Cart for the user
                Cart cart = new Cart();
                cartRepository.save(cart);

                user.setCart(cart);
                
                cart.setUser(user);


                Users createdUser = userRepository.save(user);

                return ResponseEntity.ok(new MessageResponse(createdUser, "User created successfully with a new Cart"));
            } else {
                // If the user already has a cart, handle it according to your business logic
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(HttpStatus.BAD_REQUEST.value(), "User already has a Cart"));
            }
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            
        }
    }
    @Override
    public ResponseEntity<MessageResponse> updateUser(Long userId, Users updatedUser) {
        try {
            if (userRepository.existsById(userId)) {
                updatedUser.setId(userId);
                Users savedUser = userRepository.save(updatedUser);
                MessageResponse response = new MessageResponse(savedUser, "Update successful");
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "User not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @Override
    public ResponseEntity<MessageResponse> deleteUser(Long userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return ResponseEntity.ok(new MessageResponse(null,"deleted successfully "));
            } else {
                return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "User not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
