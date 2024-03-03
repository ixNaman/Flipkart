package com.Flipkart.Flipkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Flipkart.Flipkart.model.Cart;
import com.Flipkart.Flipkart.model.Product;
import com.Flipkart.Flipkart.model.Users;
import com.Flipkart.Flipkart.repository.CartRepository;
import com.Flipkart.Flipkart.repository.ProductRepository;
import com.Flipkart.Flipkart.repository.UserRepository;
import com.Flipkart.Flipkart.response.MessageResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class CartServiceImpl implements cartService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public ResponseEntity<?> getAllCarts() {
		try {
			List<Cart> carts = cartRepository.findAll();
			if (carts.isEmpty()) {
				return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "No carts found"),
						HttpStatus.NOT_FOUND);
			} else {
				return ResponseEntity.ok(new MessageResponse(carts, "Successfully fetched all carts"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Override
	public ResponseEntity<MessageResponse> getCartById(Long cartId) {
		try {
			Optional<Cart> optionalCart = cartRepository.findById(cartId);
			if (optionalCart.isPresent()) {
				Cart cart = optionalCart.get();
				return ResponseEntity.ok(new MessageResponse(cart, "Success"));
			} else {
				return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Cart not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Override
	public ResponseEntity<MessageResponse> addProductToCart(Long userId, Long productId) {
	    try {
	        Optional<Users> userOptional = userRepository.findById(userId);

	        if (userOptional.isPresent()) {
	            Users user = userOptional.get();

	            if (user.getCart() == null) {
	                // If the user does not have a cart, create a new cart
	                Cart newCart = new Cart();
	                newCart.setUser(user);
	                entityManager.persist(newCart);
	                user.setCart(newCart);
	            }

	            Optional<Product> productOptional = productRepository.findById(productId);

	            if (productOptional.isPresent()) {
	                Product product = productOptional.get();

	                // Add the product to the user's cart
	                user.getCart().getProducts().add(product);

	                // Update the total in the cart
	                double total = user.getCart().getProducts().stream()
	                        .mapToDouble(Product::getPrice)
	                        .sum();

	                user.getCart().setTotal(total);
	                userRepository.save(user);

	                return ResponseEntity.ok(new MessageResponse(null,"Product added to the cart successfully"));
	            } else {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                        .body(new MessageResponse("Product not found with id: " + productId, null));
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(new MessageResponse("User not found with id: " + userId, null));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new MessageResponse("Error adding product to the cart", null));
	    }
	}

}
