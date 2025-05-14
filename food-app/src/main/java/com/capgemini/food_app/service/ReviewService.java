package com.capgemini.food_app.service;

import java.util.List;
import com.capgemini.food_app.model.Review;

public interface ReviewService {

	Review createReview(Review review);

	List<Review> getAllReview();

	Review getReviewById(Long id);

	Review updateReview(Long id, Review updatedReview);

	void deleteReview(Long id);

	List<Review> getReviewByRestaurant(Long restaurantId);

	List<Review> getReviewByUser(Long userId);
}