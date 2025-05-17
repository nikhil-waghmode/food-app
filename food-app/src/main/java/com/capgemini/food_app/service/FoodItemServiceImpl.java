package com.capgemini.food_app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.dto.OrderDTO;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.FoodItemRepository;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	private final FoodItemRepository foodItemRepo;
	private final RestaurantService restaurantService;
	private final Path fileStoragePath;

	@Autowired
	public FoodItemServiceImpl(FoodItemRepository foodItemRepo, RestaurantService restaurantService) {
		this.foodItemRepo = foodItemRepo;
		this.restaurantService = restaurantService;
		this.fileStoragePath = Paths.get("uploads").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload directory", e);
		}
	}

	@Override
	public List<FoodItem> getAllFoodItems() {
		return foodItemRepo.findAll();
	}

	@Override
	public FoodItem getFoodItemById(Long id) {
		return foodItemRepo.findById(id).orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));
	}

	@Override
	public FoodItem createFoodItem(FoodItem foodItem) {
		return foodItemRepo.save(foodItem);
	}

	@Override
	public FoodItem createFoodItemWithImage(String name, String category, Integer price, String cuisine,
			Long restaurantId, MultipartFile image) {
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		if (restaurant == null) {
			throw new RuntimeException("Restaurant not found with id: " + restaurantId);
		}

		String imageUrl = "";
		if (image != null && !image.isEmpty()) {
			imageUrl = saveImageFile(image);
		}

		FoodItem foodItem = new FoodItem();
		foodItem.setName(name);
		foodItem.setCategory(category);
		foodItem.setPrice(price);
		foodItem.setCuisine(cuisine);
		foodItem.setItemImg(imageUrl);
		foodItem.setRestaurant(restaurant);

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
	public String saveImageFile(MultipartFile image) {
		try {

			String extension = "";
			if (image.getOriginalFilename().contains(".")) {
				extension = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
			}
			String fileName = UUID.randomUUID().toString().substring(0, 8) + extension;

			Path targetLocation = fileStoragePath.resolve(fileName);
			Files.copy(image.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return "/img/" + fileName;
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file", ex);
		}
	}

	@Override
	public List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID) {
		return foodItemRepo.findRecentlyAddedItemByRestaurantID(restaurantID);
	}

	

}
