package com.capgemini.food_app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.food_app.model.Reviews;
import com.capgemini.food_app.repository.ReviewsRepository;

@Service
public class ReviewServiceImpl implements ReviewsService {

	@Autowired
	private ReviewsRepository reviewRepository;

	@Override
	public Reviews createReview(Reviews review) {
		return reviewRepository.save(review);
	}

	@Override
	public List<Reviews> getAllReviews() {
		return reviewRepository.findAll();
	}

	@Override
	public Reviews getReviewById(Long id) {
		return reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
	}

	@Override
	public Reviews updateReview(Long id, Reviews updatedReview) {
		Reviews existing = getReviewById(id);
		existing.setRating(updatedReview.getRating());
		existing.setFeedback(updatedReview.getFeedback());
		existing.setDate(updatedReview.getDate());
		existing.setUserId(updatedReview.getUserId());
		existing.setRestaurant(updatedReview.getRestaurant());
		return reviewRepository.save(existing);
	}

	@Override
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	}

	@Override
	public List<Reviews> getReviewsByRestaurant(Long restaurantId) {
		return reviewRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public List<Reviews> getReviewsByUser(Long userId) {
		return reviewRepository.findByUserId(userId);
	}
}
