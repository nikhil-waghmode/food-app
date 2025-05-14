package com.capgemini.food_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.capgemini.food_app.model.Reviews;
import com.capgemini.food_app.service.ReviewsService;

@RestController
@RequestMapping("/api")
public class ReviewsController {

	@Autowired
	private ReviewsService reviewService;

	@PostMapping("/reviews")
	public ResponseEntity<Reviews> createReview(@RequestBody Reviews review) {
		return ResponseEntity.status(201).body(reviewService.createReview(review));
	}

	@GetMapping("/reviews")
	public List<Reviews> getAllReviews() {
		return reviewService.getAllReviews();
	}

	@GetMapping("/reviews/{id}")
	public ResponseEntity<Reviews> getReviewById(@PathVariable Long id) {
		return ResponseEntity.ok(reviewService.getReviewById(id));
	}

	@PutMapping("/reviews/{id}")
	public ResponseEntity<Reviews> updateReview(@PathVariable Long id, @RequestBody Reviews review) {
		return ResponseEntity.ok(reviewService.updateReview(id, review));
	}

	@DeleteMapping("/reviews/{id}")
	public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
		reviewService.deleteReview(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/restaurants/{restaurantId}/reviews")
	public List<Reviews> getReviewsByRestaurant(@PathVariable Long restaurantId) {
		return reviewService.getReviewsByRestaurant(restaurantId);
	}

	@GetMapping("/users/{userId}/reviews")
	public List<Reviews> getReviewsByUser(@PathVariable Long userId) {
		return reviewService.getReviewsByUser(userId);
	} 
}