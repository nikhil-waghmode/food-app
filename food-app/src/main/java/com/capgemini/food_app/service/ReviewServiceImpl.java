package com.capgemini.food_app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.food_app.exception.ReviewNotFoundException;
import com.capgemini.food_app.model.Review;
import com.capgemini.food_app.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;

	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		super();
		this.reviewRepository = reviewRepository;
	}

	@Override
	public Review createReview(Review review) {
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getAllReview() {
		return reviewRepository.findAll();
	}

	@Override
	public Review updateReview(Long id, Review updatedReview) {
		Review existing = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found."));
		existing.setRating(updatedReview.getRating());
		existing.setFeedback(updatedReview.getFeedback());
		existing.setDate(updatedReview.getDate());
		existing.setUserId(updatedReview.getUserId());
		return reviewRepository.save(existing);
	}

	@Override
	public void deleteReview(Long id) {
		reviewRepository.deleteById(id);
	} 

	@Override
	public List<Review> getReviewByRestaurant(Long restaurantId) {
		return reviewRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public List<Review> getReviewByUser(Long userId) {
		return reviewRepository.findByUserId(userId);
	}
	@Override
	public Review patchReview(Long id, Review updatedReview) {
		Review existing=reviewRepository.findById(id).orElseThrow(() ->
		new ReviewNotFoundException("Review with ID " + id + " not found."));

		if(updatedReview.getRating()!=null) {
			existing.setRating(updatedReview.getRating());
		}
		if(updatedReview.getFeedback()!=null) {
			existing.setFeedback(updatedReview.getFeedback());
		}
		if(updatedReview.getDate()!=null) {
			existing.setDate(updatedReview.getDate());
		}
		if(updatedReview.getUserId()!=null) {
			existing.setUserId(updatedReview.getUserId());
		}		
		return reviewRepository.save(existing);
	}
	@Override
	public Review getReviewById(Long id) {
		return reviewRepository.findById(id).orElseThrow(() ->new ReviewNotFoundException("Review with ID " + id + " not found."));

	}
}