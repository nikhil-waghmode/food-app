package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.food_app.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
		boolean existsByEmail(String email);
		
		@Query("SELECT COUNT(u) FROM User u WHERE u.userType = 'OWNER'")
		int totalOwner();

		@Query("SELECT COUNT(u) FROM User u WHERE u.userType = 'CUSTOMER'")
		int totalCustomer();

		
		
		
}
