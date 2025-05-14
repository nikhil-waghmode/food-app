package com.capgemini.food_app.service;

import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    
    
    
    private final Path rootLocation = Paths.get("uploads/restaurants");

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        initializeStorage();
    }

    private void initializeStorage() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with ID: " + id));
    }

    @Override
    public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
        return restaurantRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public Long getTotalRestaurantsCount() {
        return restaurantRepository.count();
    }

//    @Override
//    public List<Object[]> restaurantsBySorted() {
//        return restaurantRepository.findRestaurantsWithOrderCount();
//    }

    @Override
    public Restaurant createRestaurant(String name, String location, String contact,
                                      Long ownerId, MultipartFile resImage) throws IOException {
        String filename = storeImage(resImage);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(location);
        restaurant.setContact(contact);
        restaurant.setOwnerId(ownerId);
        restaurant.setRestaurantImg(filename);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, String name, String location, String contact,
                                      Long ownerId, MultipartFile resImage) throws IOException {
        Restaurant existing = getRestaurantById(id);

        if (name != null) existing.setName(name);
        if (location != null) existing.setLocation(location);
        if (contact != null) existing.setContact(contact);
        if (ownerId != null) existing.setOwnerId(ownerId);
        if (resImage != null && !resImage.isEmpty()) {
            updateImage(existing, resImage);
        }

        return restaurantRepository.save(existing);
    }

    @Override
    public Restaurant patchRestaurant(Long id, String name, String location, String contact,
                                     Long ownerId, MultipartFile resImage) throws IOException {
        return updateRestaurant(id, name, location, contact, ownerId, resImage);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        deleteImage(restaurant.getRestaurantImg());
        restaurantRepository.deleteById(id);
    }

//    @Override
//    public List<Object[]> getCustomerDetailsByRestaurantID(Long restaurantID) {
//        return restaurantRepository.findCustomerDetailsByRestaurant(restaurantID);
//    }

    private String storeImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destination = rootLocation.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    private void updateImage(Restaurant restaurant, MultipartFile newImage) throws IOException {
        deleteImage(restaurant.getRestaurantImg());
        String newFilename = storeImage(newImage);
        restaurant.setRestaurantImg(newFilename);
    }

    private void deleteImage(String filename) {
        if (filename == null) return;

        try {
            Path filePath = rootLocation.resolve(filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file: " + filename, e);
        }
    }
}