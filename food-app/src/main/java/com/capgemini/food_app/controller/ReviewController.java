package com.capgemini.food_app.controller;

import java.util.List;
import com.capgemini.food_app.model.Review;
import com.capgemini.food_app.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ReviewController {

	private final ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping("/reviews")
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		log.info("Creating new review for restaurantId: {} by userId: {}",
				review.getRestaurant().getId(), review.getUserId());
		Review created = reviewService.createReview(review);
		log.debug("Created review: {}", created);
		return ResponseEntity.status(201).body(created);
	}

	@GetMapping("/reviews")
	public ResponseEntity<List<Review>> getAllReview() {
		log.info("Fetching all reviews");
		List<Review> reviews = reviewService.getAllReview();
		log.debug("Total reviews fetched: {}", reviews.size());
		return ResponseEntity.ok(reviews);
	}

	@GetMapping("/reviews/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
		log.info("Fetching review with ID: {}", id);
		Review review = reviewService.getReviewById(id);
		if (review == null) {
			log.warn("Review with ID {} not found", id);
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(review);
	}

	@PutMapping("/reviews/{id}")
	public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
		log.info("Updating review with ID: {}", id);
		Review updated = reviewService.updateReview(id, review);
		if (updated == null) {
			log.warn("Review with ID {} not found for update", id);
			return ResponseEntity.notFound().build();
		}
		log.debug("Updated review: {}", updated);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		log.info("Deleting review with ID: {}", id);
		reviewService.deleteReview(id);
		log.info("Deleted review with ID: {}", id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/restaurants/{restaurantId}/reviews")
	public ResponseEntity<List<Review>> getReviewByRestaurant(@PathVariable Long restaurantId) {
		log.info("Fetching reviews for restaurant ID: {}", restaurantId);
		List<Review> reviews = reviewService.getReviewByRestaurant(restaurantId);
		log.debug("Found {} reviews for restaurant ID {}", reviews.size(), restaurantId);
		return ResponseEntity.ok(reviews);
	}

	@GetMapping("/users/{userId}/reviews")
	public ResponseEntity<List<Review>> getReviewByUser(@PathVariable Long userId) {
		log.info("Fetching reviews by user ID: {}", userId);
		List<Review> reviews = reviewService.getReviewByUser(userId);
		log.debug("Found {} reviews by user ID {}", reviews.size(), userId);
		return ResponseEntity.ok(reviews);
	}
}
