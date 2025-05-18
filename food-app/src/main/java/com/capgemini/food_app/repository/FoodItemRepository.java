package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.food_app.model.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    @Query("SELECT f FROM FoodItem f WHERE f.restaurant.id = :restaurantId")
    List<FoodItem> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);
    
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    
    List<FoodItem> findByCuisineIgnoreCase(String cuisine);
    
    List<FoodItem> findByCategoryIgnoreCase(String category);
}