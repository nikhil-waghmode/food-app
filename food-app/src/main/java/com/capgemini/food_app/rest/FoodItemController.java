//package com.capgemini.food_app.rest;
//
//import com.capgemini.food_app.exception.FoodItemNotFoundException;
//import com.capgemini.food_app.model.FoodItem;
//import com.capgemini.food_app.service.FoodItemService;
//
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/fooditems")
//@CrossOrigin(origins = "*")
//@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")
//
//public class FoodItemController {
//
//	private final FoodItemService foodItemService;
//
//	@Autowired
//	public FoodItemController(FoodItemService foodItemService) {
//		this.foodItemService = foodItemService;
//	}
//
//	@GetMapping
//	public List<FoodItem> getAllFoodItems() {
//		return foodItemService.getAllFoodItems();
//	}
//
//	@GetMapping("/{id}")
//	public FoodItem getFoodItemById(@PathVariable Long id) {
//		return foodItemService.getFoodItemById(id);
//	}
//
//	@PostMapping
//	public FoodItem createFoodItem(@Valid @RequestBody FoodItem foodItem) {
//		return foodItemService.createFoodItem(foodItem);
//	}
//
////	@PutMapping("/{id}")
////	public FoodItem updateFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
////		return foodItemService.updateFoodItem(id, foodItem);
////	}
//
//	@PatchMapping("/{id}")
//	public FoodItem patchFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
//		return foodItemService.patchFoodItem(id, foodItem);
//	}
//
//	@DeleteMapping("/{id}")
//	public void deleteFoodItem(@PathVariable Long id) {
//		foodItemService.deleteFoodItem(id);
//	}
//
//
////    @GetMapping("/restaurant/{restaurantId}")
////    public List<FoodItem> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
////        return foodItemService.getFoodItemsForRestaurant(restaurantId);
////    }
//
//	@GetMapping("/best")
//	public ResponseEntity<FoodItem> getMostOrderedFoodItem() {
//		FoodItem foodItem = foodItemService.getMostOrderedFoodItem();
//		return foodItem != null ? ResponseEntity.ok(foodItem) : ResponseEntity.noContent().build();
//	}
//
//	@GetMapping("/least")
//	public ResponseEntity<FoodItem> getLeastOrderedFoodItem() {
//		FoodItem foodItem = foodItemService.getLeastOrderedFoodItem();
//		return foodItem != null ? ResponseEntity.ok(foodItem) : ResponseEntity.noContent().build();
//	}
//
//    @GetMapping("/restaurant/{restaurantId}")
//    public List<FoodItem> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
//        return foodItemService.getFoodItemsForRestaurant(restaurantId);
//    }
//
////	public ResponseEntity<List<FoodItem>> getAllFoodItems() {
////		List<FoodItem> items = foodItemService.getAllFoodItems();
////		return ResponseEntity.ok(items);
////	}
//
////	@GetMapping("/{id}")
////	public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
////		FoodItem item = foodItemService.getFoodItemById(id);
////		if (item == null) {
////			throw new FoodItemNotFoundException("Food item not found with id: " + id);
////		}
////		return ResponseEntity.ok(item);
////	}
//
//	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<FoodItem> createFoodItem(@RequestParam("name") String name,
//			@RequestParam("category") String category, @RequestParam("price") Integer price,
//			@RequestParam("cuisine") String cuisine, @RequestParam("restaurantID") Long restaurantId,
//			@RequestParam(value = "foodImage", required = false) MultipartFile foodImage) {
//
//		FoodItem created = foodItemService.createFoodItemWithImage(name, category, price, cuisine, restaurantId,
//				foodImage);
//		return ResponseEntity.status(HttpStatus.CREATED).body(created);
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @Valid @RequestBody FoodItem foodItem) {
//		FoodItem updated = foodItemService.updateFoodItem(id, foodItem);
//		if (updated == null) {
//			throw new FoodItemNotFoundException("Food item not found with id: " + id);
//		}
//		return ResponseEntity.ok(updated);
//	}
//
////	@PatchMapping("/{id}")
////	public ResponseEntity<FoodItem> patchFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
////		FoodItem patched = foodItemService.patchFoodItem(id, foodItem);
////		if (patched == null) {
////			throw new FoodItemNotFoundException("Food item not found with id: " + id);
////		}
////		return ResponseEntity.ok(patched);
////	}
////
////	@DeleteMapping("/{id}")
////	public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
////		boolean deleted = foodItemService.deleteFoodItem(id);
////		if (!deleted) {
////			throw new FoodItemNotFoundException("Food item not found with id: " + id);
////		}
////		return ResponseEntity.noContent().build();
////	}
//
////	@GetMapping("/restaurant/{restaurantId}")
////	public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
////		List<FoodItem> items = foodItemService.getFoodItemsForRestaurant(restaurantId);
////		return ResponseEntity.ok(items);
////	}
//
//	@GetMapping("/search")
//	public ResponseEntity<List<FoodItem>> searchFoodItems(@RequestParam String keyword) {
//		List<FoodItem> items = foodItemService.searchFoodItems(keyword);
//		return ResponseEntity.ok(items);
//	}
//
//	@GetMapping("/cuisine/{cuisine}")
//	public ResponseEntity<List<FoodItem>> getFoodItemsByCuisine(@PathVariable String cuisine) {
//		List<FoodItem> items = foodItemService.getFoodItemsByCuisine(cuisine);
//		return ResponseEntity.ok(items);
//	}
//
//	@GetMapping("/category/{category}")
//	public ResponseEntity<List<FoodItem>> getFoodItemsByCategory(@PathVariable String category) {
//		List<FoodItem> items = foodItemService.getFoodItemsByCategory(category);
//		return ResponseEntity.ok(items);
//	}
//
//	@GetMapping("/recent/{restaurantID}")
//	public ResponseEntity<List<FoodItem>> getMostRecentFoodItem(@PathVariable Long restaurantID) {
//		List<FoodItem> items = foodItemService.findRecentlyAddedItemByRestaurantID(restaurantID);
//		return ResponseEntity.ok(items);
//	}
//
//	@GetMapping("/best/{restaurantID}")
//	public ResponseEntity<List<Object[]>> getTop1FoodItemByRestaurantID(@PathVariable Long restaurantID) {
//		return ResponseEntity.status(HttpStatus.OK).body(foodItemService.getTop1FoodItemByRestaurantID(restaurantID));
//	}
//
//	@GetMapping("/least/{restaurantID}")
//	public ResponseEntity<List<Object[]>> getBottom1FoodItemByRestaurantID(@PathVariable Long restaurantID) {
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(foodItemService.getBottom1FoodItemByRestaurantID(restaurantID));
//	}
//	@GetMapping("/sold/{restaurantID}")
//	public ResponseEntity<List<Object[]>> getItemsSoldByRestaurantIDAndOnDate(@PathVariable Long restaurantID,
//			@RequestParam LocalDate date) {
//		return ResponseEntity.status(HttpStatus.OK)
//				.body(foodItemService.getItemsSoldByRestaurantIDAndOnDate(restaurantID, date));
//	}
//}

package com.capgemini.food_app.rest;

import com.capgemini.food_app.exception.FoodItemNotFoundException;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/fooditems")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")
public class FoodItemController {

    private final FoodItemService foodItemService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @GetMapping
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        return ResponseEntity.ok(foodItemService.getAllFoodItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        FoodItem item = foodItemService.getFoodItemById(id);
        if (item == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<FoodItem>> getFoodItemsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(foodItemService.getFoodItemsForRestaurant(restaurantId));
    }

    @GetMapping("/best")
    public ResponseEntity<FoodItem> getMostOrderedFoodItem() {
        FoodItem item = foodItemService.getMostOrderedFoodItem();
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.noContent().build();
    }

    @GetMapping("/least")
    public ResponseEntity<FoodItem> getLeastOrderedFoodItem() {
        FoodItem item = foodItemService.getLeastOrderedFoodItem();
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.noContent().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodItem> createFoodItem(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("price") Integer price,
            @RequestParam("cuisine") String cuisine,
            @RequestParam("restaurantID") Long restaurantId,
            @RequestParam(value = "foodImage", required = false) MultipartFile foodImage) throws IOException {

        FoodItem created = foodItemService.createFoodItem(name, category, price, cuisine, restaurantId, foodImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodItem> updateFoodItem(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("price") Integer price,
            @RequestParam("cuisine") String cuisine,
            @RequestParam("restaurantID") Long restaurantId,
            @RequestParam(value = "foodImage", required = false) MultipartFile foodImage) throws IOException {

        FoodItem updated = foodItemService.updateFoodItem(id, name, category, price, cuisine, restaurantId, foodImage);
        if (updated == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodItem> patchFoodItem(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(value = "foodImage", required = false) MultipartFile foodImage) throws IOException {

        FoodItem patched = foodItemService.patchFoodItem(id, name, category, price, cuisine, restaurantId, foodImage);
        if (patched == null) {
            throw new FoodItemNotFoundException("Food item not found with id: " + id);
        }
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> getFoodImage(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get("uploads/fooditems", filename);

        if (!Files.exists(filePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
	@GetMapping("/recent/{restaurantID}")
	public ResponseEntity<List<FoodItem>> getMostRecentFoodItem(@PathVariable Long restaurantID) {
		List<FoodItem> items = foodItemService.findRecentlyAddedItemByRestaurantID(restaurantID);
		return ResponseEntity.ok(items);
	}
	@GetMapping("/best/{restaurantID}")
	public ResponseEntity<List<Object[]>> getTop1FoodItemByRestaurantID(@PathVariable Long restaurantID) {
		return ResponseEntity.status(HttpStatus.OK).body(foodItemService.getTop1FoodItemByRestaurantID(restaurantID));
	}

	@GetMapping("/least/{restaurantID}")
	public ResponseEntity<List<Object[]>> getBottom1FoodItemByRestaurantID(@PathVariable Long restaurantID) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(foodItemService.getBottom1FoodItemByRestaurantID(restaurantID));
	}
	@GetMapping("/sold/{restaurantID}")
	public ResponseEntity<List<Object[]>> getItemsSoldByRestaurantIDAndOnDate(@PathVariable Long restaurantID,
			@RequestParam LocalDate date) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(foodItemService.getItemsSoldByRestaurantIDAndOnDate(restaurantID, date));
	}
}

