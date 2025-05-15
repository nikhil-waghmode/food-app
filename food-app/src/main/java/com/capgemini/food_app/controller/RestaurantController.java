package com.capgemini.food_app.controller;

import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        log.info("Fetching all restaurants");
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        log.debug("Total restaurants fetched: {}", restaurants.size());
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        log.info("Fetching restaurant with ID: {}", id);
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            log.debug("Restaurant details: {}", restaurant);
            return ResponseEntity.ok(restaurant);
        } catch (RuntimeException e) {
            log.warn("Restaurant with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByOwner(@PathVariable Long ownerId) {
        log.info("Fetching restaurants for owner ID: {}", ownerId);
        List<Restaurant> restaurants = restaurantService.getRestaurantsByOwner(ownerId);
        log.debug("Found {} restaurants for owner ID: {}", restaurants.size(), ownerId);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getRestaurantCount() {
        log.info("Fetching total restaurant count");
        long count = restaurantService.getTotalRestaurantsCount();
        log.debug("Total restaurants count: {}", count);
        return ResponseEntity.ok(count);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String contact,
            @RequestParam Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage) throws IOException {
        log.info("Creating new restaurant: {}", name);
        Restaurant created = restaurantService.createRestaurant(name, location, contact, ownerId, resImage);
        log.debug("Created restaurant: {}", created);
        return ResponseEntity.created(URI.create("/api/restaurants/" + created.getId())).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String location,
            @RequestParam String contact,
            @RequestParam Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage) throws IOException {
        log.info("Updating restaurant with ID: {}", id);
        try {
            Restaurant updated = restaurantService.updateRestaurant(id, name, location, contact, ownerId, resImage);
            log.debug("Updated restaurant: {}", updated);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.error("Error updating restaurant ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Restaurant> patchRestaurant(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String contact,
            @RequestParam(required = false) Long ownerId,
            @RequestParam(value = "resImage", required = false) MultipartFile resImage) throws IOException {
        log.info("Patching restaurant with ID: {}", id);
        Restaurant patched = restaurantService.patchRestaurant(id, name, location, contact, ownerId, resImage);
        log.debug("Patched restaurant: {}", patched);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.warn("Deleting restaurant with ID: {}", id);
        try {
            restaurantService.deleteRestaurant(id);
            log.info("Deleted restaurant with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting restaurant ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        log.info("Serving image: {}", filename);
        Path filePath = Paths.get("uploads/restaurants", filename);

        if (!Files.exists(filePath)) {
            log.warn("Image file not found: {}", filename);
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        log.debug("Serving image '{}' with content type '{}'", filename, contentType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
