package com.capgemini.food_app.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.capgemini.food_app.model.Review;
import com.capgemini.food_app.service.ReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")

public class ReviewController {

	private ReviewService reviewService;

	@Autowired
	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}

	@PostMapping("/reviews")
	public ResponseEntity<Review> createReview(@Valid @RequestBody Review review) {
		return ResponseEntity.status(201).body(reviewService.createReview(review));
	}

	@GetMapping("/reviews")
	public List<Review> getAllReview() {
		return reviewService.getAllReview();
	}

	@GetMapping("/reviews/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
		return ResponseEntity.ok(reviewService.getReviewById(id));
	}

	@PutMapping("/reviews/{id}")
	public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody Review review) {
		return ResponseEntity.ok(reviewService.updateReview(id, review));
	}
	@PatchMapping("/reviews/{id}")
	public ResponseEntity<Review> patchReview(@PathVariable Long id, @Valid @RequestBody Review review) {
		return ResponseEntity.ok(reviewService.updateReview(id, review));
	}

	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/restaurants/{restaurantId}/reviews")
	public List<Review> getReviewByRestaurant(@PathVariable Long restaurantId) {
		return reviewService.getReviewByRestaurant(restaurantId);
	}

	@GetMapping("/users/{userId}/reviews")
	public List<Review> getReviewByUser(@PathVariable Long userId) {
		return reviewService.getReviewByUser(userId);
	} 
}