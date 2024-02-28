package com.Flipkart.Flipkart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Flipkart.Flipkart.model.Users;

public interface UserRepository  extends JpaRepository<Users, Long> {

}
