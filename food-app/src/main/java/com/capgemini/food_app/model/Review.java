package com.capgemini.food_app.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

<<<<<<< HEAD:food-app/src/main/java/com/capgemini/food_app/model/Review.java


@Entity
@Table(name="reviews")
public class Review {
=======
@Entity
public class Reviews {
>>>>>>> d6713b2f77127967c15ee0cbf0c053c482dd5fc6:food-app/src/main/java/com/capgemini/food_app/model/Reviews.java
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
<<<<<<< HEAD:food-app/src/main/java/com/capgemini/food_app/model/Review.java
    
    public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	private Float rating;
=======
    private Float rating;
>>>>>>> d6713b2f77127967c15ee0cbf0c053c482dd5fc6:food-app/src/main/java/com/capgemini/food_app/model/Reviews.java
    private String feedback;
    private LocalDate date;
    private Long userId;

    public Review() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Review(Long id, Float rating, String feedback, LocalDate date, Long userId) {
        super();
        this.id = id;
        this.rating = rating;
        this.feedback = feedback;
        this.date = date;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reviews [id=" + id + ", rating=" + rating + ", feedback=" + feedback
                + ", date=" + date + ", userId=" + userId + "]";
    }

}
