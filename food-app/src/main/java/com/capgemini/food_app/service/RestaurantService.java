package com.capgemini.food_app.service;

import com.capgemini.food_app.model.Restaurant;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getRestaurantsByOwner(Long ownerId);
    Long getTotalRestaurantsCount();
    //List<Object[]> restaurantsBySorted();
    Restaurant createRestaurant(String name, String location, String contact, 
                               Long ownerId, MultipartFile resImage) throws IOException;
    Restaurant updateRestaurant(Long id, String name, String location, String contact,
                               Long ownerId, MultipartFile resImage) throws IOException;
    Restaurant patchRestaurant(Long id, String name, String location, String contact,
                              Long ownerId, MultipartFile resImage) throws IOException;
    void deleteRestaurant(Long id);
//    List<Object[]> getCustomerDetailsByRestaurantID(Long restaurantID);
<<<<<<< HEAD
}
=======
}
>>>>>>> d6713b2f77127967c15ee0cbf0c053c482dd5fc6
