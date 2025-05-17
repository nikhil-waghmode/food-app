package com.capgemini.food_app.rest;

import com.capgemini.food_app.exception.FoodItemNotFoundException;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.service.FoodItemService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/fooditems")
@CrossOrigin(origins = "*")
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        List<FoodItem> items = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        FoodItem item = foodItemService.getFoodItemById(id);
        if (item == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodItem> createFoodItem(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("price") Integer price,
            @RequestParam("cuisine") String cuisine,
            @RequestParam("restaurantID") Long restaurantId,
            @RequestParam(value = "foodImage", required = false) MultipartFile foodImage) {
        
        FoodItem created = foodItemService.createFoodItemWithImage(
            name, category, price, cuisine, restaurantId, foodImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
        FoodItem updated = foodItemService.updateFoodItem(id, foodItem);
        if (updated == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FoodItem> patchFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        FoodItem patched = foodItemService.patchFoodItem(id, foodItem);
        if (patched == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        boolean deleted = foodItemService.deleteFoodItem(id);
        if (!deleted) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
        List<FoodItem> items = foodItemService.getFoodItemsForRestaurant(restaurantId);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@RequestParam String keyword) {
        List<FoodItem> items = foodItemService.searchFoodItems(keyword);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByCuisine(@PathVariable String cuisine) {
        List<FoodItem> items = foodItemService.getFoodItemsByCuisine(cuisine);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable String category) {
        List<FoodItem> items = foodItemService.getFoodItemsByCategory(category);
        return ResponseEntity.ok(items);
    }
    @GetMapping("/recent/{restaurantID}")
    public ResponseEntity<List<FoodItem>> getMostRecentFoodItem(@PathVariable Long restaurantID) {
        List<FoodItem> items = foodItemService.findRecentlyAddedItemByRestaurantID(restaurantID);
        return ResponseEntity.ok(items);
    }

}
