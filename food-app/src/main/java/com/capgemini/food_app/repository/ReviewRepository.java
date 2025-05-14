package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.food_app.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	List<Review> findByRestaurantId(Long restaurantId);
	List<Review> findByUserId(Long userId);
 
}