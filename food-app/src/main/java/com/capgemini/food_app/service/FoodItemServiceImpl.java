package com.capgemini.food_app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.exception.RestaurantNotFoundException;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.FoodItemRepository;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	private final FoodItemRepository foodItemRepo;
	private final RestaurantService restaurantService;

	@Autowired
	public FoodItemServiceImpl(FoodItemRepository foodItemRepo, RestaurantService restaurantService) {
		this.foodItemRepo = foodItemRepo;
		this.restaurantService = restaurantService;
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemRepo.findAll();
	}

	@Override
	public FoodItem getFoodItemById(Long id) {
		return foodItemRepo.findById(id).orElseThrow(() -> new RestaurantNotFoundException("Food item not found with id: " + id));
	}



	@Override
	public FoodItem createFoodItem(String name, String category, Integer price, String cuisine,
			Long restaurantId, MultipartFile file) throws IOException {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
		}

		String UPLOAD_DIR = "uploads/fooditems/";

		// Create the directory if it doesn't exist
		Files.createDirectories(Paths.get(UPLOAD_DIR));

		// Generate a unique file name to avoid overwriting
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path filePath = Paths.get(UPLOAD_DIR, fileName);

		// Save the file to disk
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


		FoodItem foodItem = new FoodItem();
		foodItem.setName(name);
		foodItem.setCategory(category);
		foodItem.setPrice(price);
		foodItem.setCuisine(cuisine);
		foodItem.setItemImg(fileName);
		foodItem.setRestaurant(restaurant);

		return foodItemRepo.save(foodItem);
	}
	

	@Override
	public FoodItem updateFoodItem(Long id,String name, String category, Integer price, String cuisine,
			Long restaurantId, MultipartFile file) throws IOException {
		FoodItem existing = getFoodItemById(id);
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
		}
		// If new image is provided, replace the old one
		if (file != null && !file.isEmpty()) {
			String UPLOAD_DIR = "uploads/fooditems/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			existing.setItemImg(fileName);
		}
		
		existing.setCategory(category);
		existing.setCuisine(cuisine);
		existing.setName(name);
		existing.setPrice(price);
		existing.setRestaurant(restaurant);

		return foodItemRepo.save(existing);
	}

	@Override
	public FoodItem patchFoodItem(Long id,String name, String category, Integer price, String cuisine,
			Long restaurantId, MultipartFile file) throws IOException {
		FoodItem existing = getFoodItemById(id);
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
		}
		if (category != null)
			existing.setCategory(category);

		if (cuisine != null)
			existing.setCuisine(cuisine);

		if (name != null)
			existing.setName(name);

		if (price != null)
			existing.setPrice(price);

		if (restaurantId != null)
			existing.setRestaurant(restaurant);
		if (file != null && !file.isEmpty()) {
			String UPLOAD_DIR = "uploads/fooditems/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			existing.setItemImg(fileName);
		}
		
		return foodItemRepo.save(existing);
	}

	@Override
	public FoodItem getMostOrderedFoodItem() {
		FoodItem foodItems = foodItemRepo.findMostOrderedFoodItem();
		return foodItems;
	}

	@Override
	public FoodItem getLeastOrderedFoodItem() {
		FoodItem foodItems = foodItemRepo.findLeastOrderedFoodItem();
		return foodItems;
	}
	
	
	@Override
	public boolean deleteFoodItem(Long id) {
		if (!foodItemRepo.existsById(id)) {
			return false;
		}
		foodItemRepo.deleteById(id);
		return true;
	}

	@Override
	public List<FoodItem> getFoodItemsForRestaurant(Long restaurantId) {
		return foodItemRepo.findAllByRestaurantId(restaurantId);
	}

	@Override
	public List<FoodItem> searchFoodItems(String keyword) {
		return foodItemRepo.findByNameContainingIgnoreCase(keyword);
	}

	@Override
	public List<FoodItem> getFoodItemsByCuisine(String cuisine) {
		return foodItemRepo.findByCuisineIgnoreCase(cuisine);
	}

	@Override
	public List<FoodItem> getFoodItemsByCategory(String category) {
		return foodItemRepo.findByCategoryIgnoreCase(category);
	}


	@Override
	public List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID) {
		return foodItemRepo.findRecentlyAddedItemByRestaurantID(restaurantID);
	}

	@Override
	public List<Object[]> getBottom1FoodItemByRestaurantID(Long restaurantID) {
		return foodItemRepo.getBottom1FoodItemByRestaurantID(restaurantID);
	}

	@Override
	public List<Object[]> getTop1FoodItemByRestaurantID(Long restaurantID) {
		return foodItemRepo.getTop1FoodItemByRestaurantID(restaurantID);
	}
	@Override
	public List<Object[]> getItemsSoldByRestaurantIDAndOnDate(Long restaurantID, LocalDate date) {
		return foodItemRepo.getItemsSoldByRestaurantIDAndOnDate(restaurantID, date);
	}

}
