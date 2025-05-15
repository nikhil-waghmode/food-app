package com.capgemini.food_app.controller;


import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.service.FoodItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fooditems")
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        return foodItemService.getAllFoodItems();
    }

    @GetMapping("/{id}")
    public FoodItem getFoodItemById(@PathVariable Long id) {
        return foodItemService.getFoodItemById(id);
    }

    @PostMapping
    public FoodItem createFoodItem(@Valid @RequestBody FoodItem foodItem) {
        return foodItemService.createFoodItem(foodItem);
    }

    @PutMapping("/{id}")
    public FoodItem updateFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
        return foodItemService.updateFoodItem(id, foodItem);
    }

    @PatchMapping("/{id}")
    public FoodItem patchFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
        return foodItemService.patchFoodItem(id, foodItem);
    }

    @DeleteMapping("/{id}")
    public void deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
    }

//    @GetMapping("/restaurant/{restaurantId}")
//    public List<FoodItem> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
//        return foodItemService.getFoodItemsForRestaurant(restaurantId);
//    }
}