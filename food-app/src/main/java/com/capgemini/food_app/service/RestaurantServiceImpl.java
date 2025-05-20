package com.capgemini.food_app.service;

import com.capgemini.food_app.exception.OwnerAlreadyHasRestaurantException;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
    public Restaurant getRestaurantByOwner(Long ownerId) {
    	return restaurantRepository.findByOwnerId(ownerId).orElse(null);
    }

    @Override
    public Long getTotalRestaurantsCount() {
        return restaurantRepository.count();
    }

    @Override
    public Restaurant createRestaurant(String name, String location, String contact,
                                       Long ownerId, MultipartFile file) throws IOException {
        Restaurant existingRestaurant = getRestaurantByOwner(ownerId);

        if (existingRestaurant!= null) {
            throw new OwnerAlreadyHasRestaurantException("User already owns a restaurant");
        }

     		String UPLOAD_DIR = "uploads/restaurants/";

     		Files.createDirectories(Paths.get(UPLOAD_DIR));

     		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
     		Path filePath = Paths.get(UPLOAD_DIR, fileName);
    		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setLocation(location);
        restaurant.setContact(contact);
        restaurant.setOwnerId(ownerId);
        restaurant.setRestaurantImg(fileName);

        return restaurantRepository.save(restaurant);
    }


    @Override
    public Restaurant updateRestaurant(Long id, String name, String location, String contact,
                                      Long ownerId, MultipartFile file) throws IOException {
        Restaurant existing = getRestaurantById(id);
        if (file != null && !file.isEmpty()) {
			String UPLOAD_DIR = "uploads/restaurants/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			existing.setRestaurantImg(fileName);
		}

        
        if (name != null) existing.setName(name);
        if (location != null) existing.setLocation(location);
        if (contact != null) existing.setContact(contact);
        if (ownerId != null) existing.setOwnerId(ownerId);


        return restaurantRepository.save(existing);
    }

    @Override
    public Restaurant patchRestaurant(Long id, String name, String location, String contact,
                                     Long ownerId, MultipartFile file) throws IOException {
        Restaurant existing = getRestaurantById(id);

		if (name != null)
			existing.setName(name);
		if (location != null)
			existing.setLocation(location);
		if (contact != null)
			existing.setContact(contact);
		if (ownerId != null)
			existing.setOwnerId(ownerId);

		if (file != null && !file.isEmpty()) {
			String UPLOAD_DIR = "uploads/restaurants/";
			Files.createDirectories(Paths.get(UPLOAD_DIR));
			String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(UPLOAD_DIR, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			existing.setRestaurantImg(fileName);
		}
        return restaurantRepository.save(existing);
    }

    @Override
    public void deleteRestaurant(Long id) {
       
        restaurantRepository.deleteById(id);
    }


    @Override
    public List<Object[]> getCustomerDetailsByRestaurantID(Long restaurantID) {
        return restaurantRepository.getCustomerDetailsByRestaurantID(restaurantID);
    }

}
