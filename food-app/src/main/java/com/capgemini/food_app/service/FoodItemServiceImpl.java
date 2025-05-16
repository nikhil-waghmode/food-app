package com.capgemini.food_app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.repository.FoodItemRepository;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	private final FoodItemRepository foodItemRepo;

	public FoodItemServiceImpl(FoodItemRepository foodItemRepo) {
		this.foodItemRepo = foodItemRepo;
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemRepo.findAll();
	}

	@Override
	public FoodItem getFoodItemById(Long id) {
		return foodItemRepo.findById(id).orElseThrow(() -> new RuntimeException("Food item not found by id"));
	}

	@Override
	public FoodItem createFoodItem(FoodItem foodItem) {
		return foodItemRepo.save(foodItem);
	}

	@Override
	public FoodItem updateFoodItem(Long id, FoodItem foodItem) {
		FoodItem existing = getFoodItemById(id);
		existing.setCategory(foodItem.getCategory());
		existing.setCuisine(foodItem.getCuisine());
		existing.setItemImg(foodItem.getItemImg());
		existing.setName(foodItem.getName());
		existing.setPrice(foodItem.getPrice());
		existing.setRestaurant(foodItem.getRestaurant());

		return foodItemRepo.save(existing);

	}

	@Override
	public void deleteFoodItem(Long id) {
		foodItemRepo.deleteById(id);

	}

	@Override
	public List<FoodItem> getFoodItemsForRestaurant(Long restaurantID) {
		return foodItemRepo.findAllByRestaurantId(restaurantID);
	}

	@Override
	public FoodItem patchFoodItem(Long id, FoodItem foodItem) {
		FoodItem existing = getFoodItemById(id);

		if (foodItem.getCategory() != null)
			existing.setCategory(foodItem.getCategory());

		if (foodItem.getCuisine() != null)
			existing.setCuisine(foodItem.getCuisine());

		if (foodItem.getItemImg() != null)
			existing.setItemImg(foodItem.getItemImg());

		if (foodItem.getName() != null)
			existing.setName(foodItem.getName());

		if (foodItem.getPrice() != null)
			existing.setPrice(foodItem.getPrice());

		if (foodItem.getRestaurant() != null)
			existing.setRestaurant(foodItem.getRestaurant());

		return foodItemRepo.save(existing);
	}

	// @Override
	// public List<Object[]> getTop1FoodItemForAdmin() {
	// return foodItemRepo.getTop1FoodItemForAdmin();
	// }
	//
	// @Override
	// public List<Object[]> getBottom1FoodItemForAdmin() {
	// return foodItemRepo.getBottom1FoodItemForAdmin();
	// }

}