package com.capgemini.food_app;

import com.capgemini.food_app.controller.ReviewController;
import com.capgemini.food_app.model.Review;
import com.capgemini.food_app.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReview() {
        Review input = new Review(null, 5F, "Great!", LocalDate.now(), 1L);
        Review saved = new Review(1L, 5F, "Great!", LocalDate.now(), 1L);

        when(reviewService.createReview(input)).thenReturn(saved);

        ResponseEntity<Review> response = reviewController.createReview(input);

        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(saved);
    }

    @Test
    void testGetAllReviews() {
        Review r1 = new Review(1L, 5F, "Nice", LocalDate.now(), 1L);
        Review r2 = new Review(2L, 4F, "Good", LocalDate.now(), 2L);
        List<Review> list = Arrays.asList(r1, r2);

        when(reviewService.getAllReview()).thenReturn(list);

        List<Review> result = reviewController.getAllReview();

        assertThat(result).hasSize(2).contains(r1, r2);
    }

    @Test
    void testGetReviewById() {
        Review review = new Review(1L, 5F, "Excellent", LocalDate.now(), 1L);

        when(reviewService.getReviewById(1L)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.getReviewById(1L);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(review);
    }

    @Test
    void testUpdateReview() {
        Review update = new Review(null, 4F, "Updated", LocalDate.now(), 1L);
        Review updated = new Review(1L, 4F, "Updated", LocalDate.now(), 1L);

        when(reviewService.updateReview(1L, update)).thenReturn(updated);

        ResponseEntity<Review> response = reviewController.updateReview(1L, update);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(updated);
    }

    @Test
    void testDeleteReview() {
        doNothing().when(reviewService).deleteReview(1L);

        ResponseEntity<Void> response = reviewController.deleteReview(1L);

        // assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(reviewService, times(1)).deleteReview(1L);
    }

    @Test
    void testGetReviewByRestaurant() {
        Review r1 = new Review(1L, 4F, "Good", LocalDate.now(), 2L);
        when(reviewService.getReviewByRestaurant(100L)).thenReturn(List.of(r1));

        List<Review> result = reviewController.getReviewByRestaurant(100L);

        assertThat(result).containsExactly(r1);
    }

    @Test
    void testGetReviewByUser() {
        Review r1 = new Review(1L, 3F, "Okay", LocalDate.now(), 99L);
        when(reviewService.getReviewByUser(99L)).thenReturn(List.of(r1));

        List<Review> result = reviewController.getReviewByUser(99L);

        assertThat(result).containsExactly(r1);
    }
}
