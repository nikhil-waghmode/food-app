package com.capgemini.food_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.food_app.dto.TopRestaurantDTO;
import com.capgemini.food_app.repository.RestaurantRepository;

@Service
public class TopRestaurantDTOServiceImpl implements TopRestaurantDTOService {
	
	RestaurantRepository restaurantRepository;
	
	public TopRestaurantDTOServiceImpl(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@Override
	public List<TopRestaurantDTO> findTopRestaurantsByAverageRating() {
		return restaurantRepository.findTopRestaurantsByAverageRating();
	}
}
