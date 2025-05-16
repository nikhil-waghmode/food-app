package com.capgemini.food_app.service;

import java.util.List;

import com.capgemini.food_app.model.FoodItem;

public interface FoodItemService {

	List<FoodItem> getAllFoodItems();

	FoodItem getFoodItemById(Long id);

	FoodItem createFoodItem(FoodItem foodItem);

	FoodItem updateFoodItem(Long id, FoodItem foodItem);

	FoodItem patchFoodItem(Long id, FoodItem foodItem);

	void deleteFoodItem(Long id);

	List<FoodItem> getFoodItemsForRestaurant(Long restaurantID);

	// public List<Object[]> getBottom1FoodItemForAdmin();
	//
	// public List<Object[]> getTop1FoodItemForAdmin();

	FoodItem getMostOrderedFoodItem();

	FoodItem getLeastOrderedFoodItem();

}