package com.capgemini.food_app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.food_app.exception.FoodItemNotFoundException;
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
        return foodItemRepo.findById(id)
                .orElseThrow(() -> new FoodItemNotFoundException("Food item not found with id: " + id));
    }
    
    @Override
    public FoodItem createFoodItemWithImage( String name, String category, Integer price, 
    		 String cuisine, Long restaurantId, MultipartFile file) throws IOException {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + restaurantId);
        }
        
        String UPLOAD_DIR = "uploads/fooditems/";
        
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
    public void deleteFoodItem(Long id) {
        if (!foodItemRepo.existsById(id)) {
        	throw new FoodItemNotFoundException("Food Item not found with ID:" + id);
        }
        foodItemRepo.deleteById(id);
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
    

}