package com.Flipkart.Flipkart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Flipkart.Flipkart.model.Product;
import com.Flipkart.Flipkart.response.MessageResponse;
import com.Flipkart.Flipkart.service.productService;


@RestController
@RequestMapping("/api/products")
public class productController {

	@Autowired
	private productService productService;

	@GetMapping("/getAllProducts")
	public ResponseEntity<?> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{productId}")
	public ResponseEntity<MessageResponse> getProductById(@RequestParam Long productId) {
		return productService.getProductById(productId);
	}

	@PostMapping("/create")
    public ResponseEntity<MessageResponse> createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        return productService.updateProduct(productId, updatedProduct);
    }

   

}
