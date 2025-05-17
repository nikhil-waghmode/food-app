package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

	@Query("SELECT f FROM FoodItem f WHERE f.restaurant.id = :restaurantId")
	List<FoodItem> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);

	List<FoodItem> findByNameContainingIgnoreCase(String name);

	List<FoodItem> findByCuisineIgnoreCase(String cuisine);

	List<FoodItem> findByCategoryIgnoreCase(String category);

	@Query(value = "SELECT * FROM food_items WHERE restaurant_id = ?1 ORDER BY id DESC LIMIT 3", nativeQuery = true)
	List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID);

}
