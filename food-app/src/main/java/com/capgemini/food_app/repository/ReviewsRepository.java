package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.food_app.model.Reviews;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
	
	List<Reviews> findByRestaurantId(Long restaurantId);
	List<Reviews> findByUserId(Long userId);
 
}
