package com.Flipkart.Flipkart.service;

import com.Flipkart.Flipkart.model.Cart;
import com.Flipkart.Flipkart.model.Orders;
import com.Flipkart.Flipkart.model.Product;
import com.Flipkart.Flipkart.model.Users;
import com.Flipkart.Flipkart.repository.CartRepository;
import com.Flipkart.Flipkart.repository.OrderRepository;
import com.Flipkart.Flipkart.repository.UserRepository;
import com.Flipkart.Flipkart.response.MessageResponse;
import com.Flipkart.Flipkart.response.OrderProductDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class orderServiceImpl implements orderService {

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private OrderRepository ordersRepository;
    
    @Transactional
    public ResponseEntity<MessageResponse> placeOrder(Long cartId) {
        try {
            Optional<Cart> cartOptional = cartRepository.findById(cartId);
 
            if (cartOptional.isPresent()) {
                Cart cart = cartOptional.get();
 
                Set<Product> productsInCart = cart.getProducts();
 
                if (!productsInCart.isEmpty()) {
                    Orders newOrder = new Orders();
                    newOrder.setOrderNumber(generateOrderNumber());
                    newOrder.setOrderDate(new Date());
                    Set<Product> orderedProducts = new HashSet<>();
        		    
        		    // Iterate over products in the cart and add them to the order's product set
        		    for (Product product : productsInCart) {
        		        orderedProducts.add(product);
        		    }
        		    
        		    newOrder.setProducts(orderedProducts); 
         
                    newOrder.setCart(cart);  
 
                    cart.getOrders().add(newOrder);
 
                    cart.getProducts().clear();
                    cart.setTotal(0.0);
 
                    cartRepository.save(cart);
                    ordersRepository.save(newOrder);
 
                    return ResponseEntity.ok(new MessageResponse( newOrder,"Products ordered successfully"));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new MessageResponse("Cart is empty. Cannot place an order.", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Cart not found with id: " + cartId, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse());
        }
    }

    private String generateOrderNumber() {
        Long latestOrderNumber = ordersRepository.findLatestOrderNumber();
        if (latestOrderNumber == null) {
            return "ord-1";
        } else {
            return "ord-" + (latestOrderNumber + 1);
        }
    }
    
    
    @Override
	public ResponseEntity<MessageResponse> getOrderedProductsByUser(Long userId) {
		Optional<Users> userOptional = userRepository.findById(userId);
		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageResponse("User not found with ID: " + userId, null));
		}
 
		// Retrieve orders for the user
		Users user = userOptional.get();
		Cart cart = user.getCart();
		Set<Orders> orders = cart.getOrders();
		List<OrderProductDTO> orderedProducts = new ArrayList<>();
		for (Orders order : orders) {
			for (Product product : order.getProducts()) {
				// Check if the product exists in the DTO list
				Optional<OrderProductDTO> existingProduct = orderedProducts.stream()
						.filter(dto -> dto.getProductId().equals(product.getId())).findFirst();
 
				// If product exists, increment the count, else add a new DTO
				if (existingProduct.isPresent()) {
					existingProduct.get().incrementCount();
				} else {
					orderedProducts.add(new OrderProductDTO(order.getCart().getUser().getUsername(),
							order.getId(), product.getId(), product.getName(), 1));
				}
			}
		}
 
		// Return the ordered products along with a success message
		return ResponseEntity.ok(new MessageResponse( orderedProducts,"Ordered products retrieved successfully"));
	}
    
  
    
    

}
