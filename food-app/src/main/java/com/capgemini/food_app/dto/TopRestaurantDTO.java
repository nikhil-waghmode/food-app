package com.capgemini.food_app.dto;

import com.capgemini.food_app.model.Restaurant;

public class TopRestaurantDTO {
	private Restaurant restaurant;
    private Double averageRating;
    
    

    public TopRestaurantDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopRestaurantDTO(Restaurant restaurant, Double averageRating) {
        this.restaurant = restaurant;
        this.averageRating = averageRating;
    }

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}
    
    
}
