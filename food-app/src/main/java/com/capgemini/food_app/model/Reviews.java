package com.capgemini.food_app.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Reviews {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="restaurant_id")
    private List<Restaurant>restaurant;
    private Float rating;
    private String feedback;
    private LocalDate date;
    private Long userId;

    public List<Restaurant> getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(List<Restaurant> restaurant) {
		this.restaurant = restaurant;
	}

	public Reviews() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Reviews(Long id, Float rating, String feedback, LocalDate date, Long userId) {
        super();
        this.id = id;
        this.rating = rating;
        this.feedback = feedback;
        this.date = date;
        this.userId = userId;
    }
    

    public Reviews(Long id, List<Restaurant> restaurant, Float rating, String feedback, LocalDate date, Long userId) {
		super();
		this.id = id;
		this.restaurant = restaurant;
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
