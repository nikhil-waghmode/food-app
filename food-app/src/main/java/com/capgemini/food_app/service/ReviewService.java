package com.capgemini.food_app.service;

import java.util.List;
import com.capgemini.food_app.model.Review;

public interface ReviewService {

	Review createReview(Review review);

	List<Review> getAllReview();

	Review updateReview(Long id, Review updatedReview);

	void deleteReview(Long id);
	
	Review patchReview(Long id, Review review);

	List<Review> getReviewByRestaurant(Long restaurantId);

	List<Review> getReviewByUser(Long userId);
	
	public Review getReviewById(Long id);
}