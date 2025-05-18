package com.capgemini.food_app;

import com.capgemini.food_app.model.Review;
import com.capgemini.food_app.repository.ReviewRepository;
import com.capgemini.food_app.service.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class ReviewServiceImplTest {
	@Mock
	private ReviewRepository reviewRepository;

	@InjectMocks
	private ReviewServiceImpl reviewService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateReview() {
		Review review = new Review(null, 5F, "Nice", LocalDate.now(), 1L);
		Review saved = new Review(1L, 5F, "Nice", LocalDate.now(), 1L);

		when(reviewRepository.save(review)).thenReturn(saved);

		Review result = reviewService.createReview(review);

		assertThat(result).isEqualTo(saved);
	}

	@Test
	void testGetAllReviews() {
		List<Review> reviews = Arrays.asList(
				new Review(1L, 5F, "Great", LocalDate.now(), 1L),
				new Review(2L, 4F, "Good", LocalDate.now(), 2L));

		when(reviewRepository.findAll()).thenReturn(reviews);

		List<Review> result = reviewService.getAllReview();

		assertThat(result).hasSize(2).containsAll(reviews);
	}

	@Test
	void testGetReviewById_found() {
		Review review = new Review(1L, 4F, "Okay", LocalDate.now(), 1L);

		when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

		Review result = reviewService.getReviewById(1L);

		assertThat(result).isEqualTo(review);
	}

	@Test
	void testGetReviewById_notFound() {
		when(reviewRepository.findById(100L)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> reviewService.getReviewById(100L))
				.isInstanceOf(com.capgemini.food_app.exception.ReviewNotFoundException.class)
				.hasMessageContaining("Review with ID 100 not found.");
	}

	@Test
	void testUpdateReview() {
		Review existing = new Review(1L, 3F, "Old", LocalDate.of(2024, 1, 1), 1L);
		Review updated = new Review(null, 5F, "Updated", LocalDate.of(2025, 1, 1), 2L);
		Review saved = new Review(1L, 5F, "Updated", LocalDate.of(2025, 1, 1), 2L);

		when(reviewRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(reviewRepository.save(any(Review.class))).thenReturn(saved);

		Review result = reviewService.updateReview(1L, updated);

		assertThat(result).isEqualTo(saved);
		assertThat(result.getFeedback()).isEqualTo("Updated");
		assertThat(result.getRating()).isEqualTo(5);
	}

	@Test
	void testDeleteReview() {
		doNothing().when(reviewRepository).deleteById(1L);

		reviewService.deleteReview(1L);

		verify(reviewRepository, times(1)).deleteById(1L);
	}

	@Test
	void testGetReviewByRestaurant() {
		List<Review> reviews = List.of(new Review(1L, 5F, "Yummy", LocalDate.now(), 10L));

		when(reviewRepository.findByRestaurantId(10L)).thenReturn(reviews);

		List<Review> result = reviewService.getReviewByRestaurant(10L);

		assertThat(result).isEqualTo(reviews);
	}

	@Test
	void testGetReviewByUser() {
		List<Review> reviews = List.of(new Review(1L, 4F, "User review", LocalDate.now(), 2L));

		when(reviewRepository.findByUserId(20L)).thenReturn(reviews);

		List<Review> result = reviewService.getReviewByUser(20L);

		assertThat(result).isEqualTo(reviews);
	}
}
