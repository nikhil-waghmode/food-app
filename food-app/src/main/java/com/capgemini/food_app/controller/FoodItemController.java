package com.capgemini.food_app.controller;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.service.FoodItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fooditems")
@Slf4j
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        log.info("Fetching all food items");
        List<FoodItem> items = foodItemService.getAllFoodItems();
        log.info("Fetched {} food items", items.size());
        return items;
    }

    @GetMapping("/{id}")
    public FoodItem getFoodItemById(@PathVariable Long id) {
        log.info("Fetching food item with id {}", id);
        FoodItem item = foodItemService.getFoodItemById(id);
        if (item == null) {
            log.warn("Food item with id {} not found", id);
        } else {
            log.info("Found food item: {}", item);
        }
        return item;
    }

    @PostMapping
    public FoodItem createFoodItem(@RequestBody FoodItem foodItem) {
        log.info("Creating new food item: {}", foodItem.getName());
        FoodItem created = foodItemService.createFoodItem(foodItem);
        log.info("Created food item with ID {}", created.getId());
        return created;
    }

    @PutMapping("/{id}")
    public FoodItem updateFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        log.info("Updating food item with id {}", id);
        FoodItem updated = foodItemService.updateFoodItem(id, foodItem);
        log.debug("Updated food item: {}", updated);
        return updated;
    }

    @PatchMapping("/{id}")
    public FoodItem patchFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        log.trace("Patching food item with id {}", id);
        FoodItem patched = foodItemService.patchFoodItem(id, foodItem);
        log.debug("Patched food item: {}", patched);
        return patched;
    }

    @DeleteMapping("/{id}")
    public void deleteFoodItem(@PathVariable Long id) {
        log.warn("Deleting food item with id {}", id);
        try {
            foodItemService.deleteFoodItem(id);
            log.info("Successfully deleted food item with id {}", id);
        } catch (Exception e) {
            log.error("Error deleting food item with id {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // @GetMapping("/restaurant/{restaurantId}")
    // public List<FoodItem> getFoodItemsByRestaurantId(@PathVariable Long
    // restaurantId) {
    // log.info("Fetching food items for restaurant id {}", restaurantId);
    // List<FoodItem> items =
    // foodItemService.getFoodItemsForRestaurant(restaurantId);
    // log.debug("Found {} items for restaurant id {}", items.size(), restaurantId);
    // return items;
    // }
}
