package com.capgemini.food_app.repository;

import com.capgemini.food_app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByOwnerId(Long ownerId);

    // @Query("SELECT r.id, r.name, COUNT(o.id) FROM Restaurant r LEFT JOIN
    // r.foodOrders o " +
    // "GROUP BY r.id ORDER BY COUNT(o.id) DESC")
    // List<Object[]> findRestaurantsWithOrderCount();

    // @Query("SELECT c.name, c.email, o.orderDate FROM FoodOrder o " +
    // "JOIN o.customer c WHERE o.restaurant.id = ?1")
    // List<Object[]> findCustomerDetailsByRestaurant(Long restaurantId);

    // @Query(value = "SELECT r.* FROM restaurant r ORDER BY r.rating DESC LIMIT 3",
    // nativeQuery = true)
    // List<Object[]> findTop1ByOrderByRatingDesc();

    // @Query(value = "SELECT r.* FROM restaurant r ORDER BY r.rating ASC LIMIT 3",
    // nativeQuery = true)
    // List<Object[]> findTop1ByOrderByRatingAsc();

}