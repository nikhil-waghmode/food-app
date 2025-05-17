package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.capgemini.food_app.model.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long>{

	@Query("SELECT f FROM FoodItem f WHERE f.restaurant.id = :restaurantId")
    List<FoodItem> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);
	
	@Query(value = "SELECT fi.* FROM food_items fi " +
            "JOIN order_items oi ON oi.item_id = fi.id " +
            "GROUP BY fi.id " +
            "ORDER BY COUNT(oi.id) DESC LIMIT 1", nativeQuery = true)
    FoodItem findMostOrderedFoodItem();

    @Query(value = "SELECT fi.* FROM food_items fi " +
            "JOIN order_items oi ON oi.item_id = fi.id " +
            "GROUP BY fi.id " +
            "ORDER BY COUNT(oi.id) ASC LIMIT 1", nativeQuery = true)
    FoodItem findLeastOrderedFoodItem();
}