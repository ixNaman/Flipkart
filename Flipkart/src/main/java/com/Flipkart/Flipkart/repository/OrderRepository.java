package com.Flipkart.Flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Flipkart.Flipkart.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	 @Query("SELECT MAX(CAST(SUBSTRING(o.orderNumber, 5) AS long)) FROM Orders o")
	 Long findLatestOrderNumber();

}
