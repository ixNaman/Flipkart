package com.Flipkart.Flipkart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Flipkart.Flipkart.model.Product;
import com.Flipkart.Flipkart.repository.ProductRepository;
import com.Flipkart.Flipkart.response.MessageResponse;

@Service
public class productServiceImpl implements productService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ResponseEntity<?> getAllProducts() {
		try {
			List<Product> products = productRepository.findAll();
			if (products.isEmpty()) {
				return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Products not found"),
						HttpStatus.NOT_FOUND);
			} else {
				return ResponseEntity.ok(new MessageResponse(products, "Successfully fetched all products"));
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<MessageResponse> getProductById(Long productId) {
		try {
			Optional<Product> optionalProduct = productRepository.findById(productId);
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				return ResponseEntity.ok(new MessageResponse(product, "Successfully fetched product by ID"));
			} else {
				return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Product not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Override
	public ResponseEntity<MessageResponse> createProduct(Product product) {
		try {
			Product createdProduct = productRepository.save(product);
			return ResponseEntity.ok(new MessageResponse(createdProduct, "Successfully added product"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@Override
	public ResponseEntity<MessageResponse> updateProduct(Long productId, Product updatedProduct) {
		try {
			// Check if the product exists
			Optional<Product> existingProductOptional = productRepository.findById(productId);

			if (existingProductOptional.isPresent()) {
				Product existingProduct = existingProductOptional.get();

				// Check if the product is associated with any cart
				boolean isProductInCart = !existingProduct.getCarts().isEmpty();

				if (!isProductInCart) {
					// If the product is not in any cart, proceed with the update
					updatedProduct.setId(productId);
					Product savedProduct = productRepository.save(updatedProduct);
					MessageResponse response = new MessageResponse(savedProduct, "Product update successful");
					return ResponseEntity.ok(response);
				} else {
					// Product is in a cart, cannot update
					return new ResponseEntity<>(
							new MessageResponse(HttpStatus.BAD_REQUEST.value(), "Product is in a cart. Cannot update."),
							HttpStatus.BAD_REQUEST);
				}
			} else {
				// Product not found
				return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND.value(), "Product not found"),
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

}
