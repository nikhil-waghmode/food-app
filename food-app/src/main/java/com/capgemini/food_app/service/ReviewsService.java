package com.capgemini.food_app.service;

import java.util.List;
import com.capgemini.food_app.model.Reviews;

public interface ReviewsService {

	Reviews createReview(Reviews review);

	List<Reviews> getAllReviews();

	Reviews getReviewById(Long id);

	Reviews updateReview(Long id, Reviews updatedReview);

	void deleteReview(Long id);

	List<Reviews> getReviewsByRestaurant(Long restaurantId);

	List<Reviews> getReviewsByUser(Long userId);
} 
