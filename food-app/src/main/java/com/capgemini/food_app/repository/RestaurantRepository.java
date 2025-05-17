package com.capgemini.food_app.repository;

import com.capgemini.food_app.dto.TopRestaurantDTO;
import com.capgemini.food_app.dto.DailyOrderSummaryDTO;
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

    
    @Query("SELECT new com.capgemini.food_app.dto.TopRestaurantDTO(r, AVG(rev.rating)) " +
            "FROM Restaurant r JOIN r.review rev " +
            "GROUP BY r " +
            "ORDER BY AVG(rev.rating) DESC")
     List<TopRestaurantDTO> findTopRestaurantsByAverageRating();

    

    @Query(value = """
    	    SELECT r.name AS restaurantName,
    	           o.date AS date,
    	           COUNT(*) AS totalOrders,
    	           SUM(o.total_amount) AS totalRevenue
    	    FROM orders o
    	    JOIN restaurants r ON o.restaurant_id = r.id
    	    GROUP BY r.name, o.date
    	    ORDER BY o.date DESC
    	    """, nativeQuery = true)
    	List<DailyOrderSummaryDTO> fetchDailyOrderSummary();
 

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


