package com.capgemini.food_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.dto.OrderDTO;
import com.capgemini.food_app.model.FoodItem;

public interface FoodItemService {

	List<FoodItem> getAllFoodItems();

	FoodItem getFoodItemById(Long id);

	FoodItem createFoodItem(FoodItem foodItem);

	FoodItem createFoodItemWithImage(String name, String category, Integer price, String cuisine, Long restaurantId,
			MultipartFile image);

	FoodItem updateFoodItem(Long id, FoodItem foodItem);

	FoodItem patchFoodItem(Long id, FoodItem foodItem);

	boolean deleteFoodItem(Long id);

	List<FoodItem> getFoodItemsForRestaurant(Long restaurantId);

	List<FoodItem> searchFoodItems(String keyword);

	List<FoodItem> getFoodItemsByCuisine(String cuisine);

	List<FoodItem> getFoodItemsByCategory(String category);

	String saveImageFile(MultipartFile image);

	List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID);

	List<Object[]> getTop1FoodItemByRestaurantID(Long restaurantID);

	List<Object[]> getBottom1FoodItemByRestaurantID(Long restaurantID);
	public List<Object[]> getItemsSoldByRestaurantIDAndOnDate(Long restaurantID, LocalDate date);
}
