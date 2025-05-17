package com.capgemini.food_app.service;

import java.util.List;

import com.capgemini.food_app.dto.TopRestaurantDTO;

public interface TopRestaurantDTOService {
	List<TopRestaurantDTO> findTopRestaurantsByAverageRating();
}
