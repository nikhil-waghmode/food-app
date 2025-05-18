package com.capgemini.food_app.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.model.FoodItem;

public interface FoodItemService {

	List<FoodItem> getAllFoodItems();

	FoodItem getFoodItemById(Long id);


	FoodItem createFoodItem(String name, String category, Integer price, String cuisine, Long restaurantId,
			MultipartFile image) throws IOException;

	FoodItem updateFoodItem(Long id,String name, String category, Integer price, String cuisine,
			Long restaurantId, MultipartFile file) throws IOException;
	
	public FoodItem patchFoodItem(Long id, String name, String category, Integer price, String cuisine,
            Long restaurantId, MultipartFile image) throws IOException;
	boolean deleteFoodItem(Long id);

	
	FoodItem getMostOrderedFoodItem();

	FoodItem getLeastOrderedFoodItem();
	
	List<FoodItem> getFoodItemsForRestaurant(Long restaurantId);


	List<FoodItem> searchFoodItems(String keyword);

	List<FoodItem> getFoodItemsByCuisine(String cuisine);

	List<FoodItem> getFoodItemsByCategory(String category);

	List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID);

	List<Object[]> getTop1FoodItemByRestaurantID(Long restaurantID);

	List<Object[]> getBottom1FoodItemByRestaurantID(Long restaurantID);
	
	public List<Object[]> getItemsSoldByRestaurantIDAndOnDate(Long restaurantID, LocalDate date);
}
