package com.capgemini.food_app.rest;

import com.capgemini.food_app.dto.DailyOrderSummaryDTO;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.RestaurantRepository;
import com.capgemini.food_app.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")

public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantController(RestaurantService restaurantService,RestaurantRepository restaurantRepository) {
        this.restaurantService = restaurantService;
        this.restaurantRepository=restaurantRepository;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Restaurant> getRestaurantsByOwner(@PathVariable Long ownerId) {
//        return ResponseEntity.ok(restaurantService.getRestaurantsByOwner(ownerId));
        return ResponseEntity.status(200).body(restaurantService.getRestaurantByOwner(ownerId));

    }

    @GetMapping("/count")
    public ResponseEntity<Long> getRestaurantCount() {
        return ResponseEntity.ok(restaurantService.getTotalRestaurantsCount());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String contact,
            @RequestParam Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage
    ) throws IOException {
        Restaurant created = restaurantService.createRestaurant(name, location, contact, ownerId, resImage);
        return ResponseEntity.created(URI.create("/api/restaurants/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String contact,
            @RequestParam Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage
    ) throws IOException {
        Restaurant updated = restaurantService.updateRestaurant(id, name, location, contact, ownerId, resImage);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> patchRestaurant(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String contact,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage
    ) throws IOException {
        Restaurant patched = restaurantService.patchRestaurant(id, name, location, contact, ownerId, resImage);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

	@GetMapping("/image/{filename}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {

		Path filePath = Paths.get("uploads/fooditems", filename);

		if (!Files.exists(filePath)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		Resource resource = new UrlResource(filePath.toUri());

		String contentType = Files.probeContentType(filePath);
		if (contentType == null) {
			contentType = "application/octet-stream"; // default fallback
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
	
	@GetMapping("/daily-orders")
    public List<DailyOrderSummaryDTO> getDailyOrderSummary() {
        return restaurantRepository.fetchDailyOrderSummary();
    }
}