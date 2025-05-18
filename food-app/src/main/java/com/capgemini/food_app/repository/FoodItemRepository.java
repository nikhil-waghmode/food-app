package com.capgemini.food_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.FoodItem;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

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


	List<FoodItem> findByNameContainingIgnoreCase(String name);

	List<FoodItem> findByCuisineIgnoreCase(String cuisine);

	List<FoodItem> findByCategoryIgnoreCase(String category);

	@Query(value = "SELECT * FROM food_items WHERE restaurant_id = ?1 ORDER BY id DESC LIMIT 3", nativeQuery = true)
	List<FoodItem> findRecentlyAddedItemByRestaurantID(Long restaurantID);
	
	@NativeQuery("Select fi.id as fooditem_id,fi.name as fooditems, count(*) as ordered from food_items fi, orders o, order_items oi, restaurants r Where oi.item_id = fi.id and oi.order_id = o.id and r.id=o.restaurant_id and r.id = ?1 Group by fi.id order by ordered desc LIMIT 1;")
	List<Object[]> getTop1FoodItemByRestaurantID(Long restaurantID);

	@NativeQuery("Select fi.id as fooditem_id,fi.name as fooditems, count(*) as ordered from food_items fi, orders o, order_items oi, restaurants r Where oi.item_id = fi.id and oi.order_id = o.id and r.id=o.restaurant_id and r.id = ?1 Group by fi.id order by ordered asc LIMIT 1;")
	List<Object[]> getBottom1FoodItemByRestaurantID(Long restaurantID);
	
	@NativeQuery("SELECT fi.name, oi.quantity FROM food_items fi, order_items oi, orders o WHERE oi.item_id = fi.id AND oi.order_id = o.id AND o.restaurant_id = ?1 AND o.date = ?2;")
	List<Object[]> getItemsSoldByRestaurantIDAndOnDate(Long restaurantID, LocalDate date);

}
