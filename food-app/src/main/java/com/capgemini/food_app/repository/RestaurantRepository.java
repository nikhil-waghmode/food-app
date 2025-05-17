package com.capgemini.food_app.repository;

import com.capgemini.food_app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByOwnerId(Long ownerId);

//    @Query("SELECT r.id, r.name, COUNT(o.id) FROM Restaurant r LEFT JOIN r.foodOrders o " +
//           "GROUP BY r.id ORDER BY COUNT(o.id) DESC")
//    List<Object[]> findRestaurantsWithOrderCount();

//    @Query("SELECT c.name, c.email, o.orderDate FROM FoodOrder o " +
//           "JOIN o.customer c WHERE o.restaurant.id = ?1")
//    List<Object[]> findCustomerDetailsByRestaurant(Long restaurantId);

 // In RestaurantRepository.java
    @Query(value = "SELECT u.id AS customer_id, u.name AS customer_name, " +
           "COUNT(o.id) AS total_orders, SUM(o.total_amount) AS total_spend, " +
           "MAX(o.date) AS last_order_date " +
           "FROM users u " +
           "JOIN orders o ON u.id = o.user_id " +
           "WHERE o.restaurant_id = ?1 " +
           "GROUP BY u.id, u.name " +
           "ORDER BY total_spend DESC LIMIT 3", nativeQuery = true)
    List<Object[]> getCustomerDetailsByRestaurantID(Long restaurantID);

}


