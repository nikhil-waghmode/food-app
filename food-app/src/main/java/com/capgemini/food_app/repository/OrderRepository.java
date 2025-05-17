package com.capgemini.food_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;

// edited
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	boolean existsById(Long id);

	List<Order> findByRestaurantId(Long restaurantId);

	// Total order count for a restaurant (all time)
	@Query("SELECT COUNT(o) FROM Order o WHERE o.restaurantId = :restaurantId")
	Long getOrderCountByRestaurant(@Param("restaurantId") Long restaurantId);

	// Total order count for a restaurant on a specific date
	@Query("SELECT COUNT(o) FROM Order o WHERE o.restaurantId = :restaurantId AND o.date = :date")
	Long getOrderCountByRestaurantAndDate(@Param("restaurantId") Long restaurantId, @Param("date") LocalDate date);

	// Total revenue for a restaurant (all time)
	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.restaurantId = :restaurantId")
	Double getTotalRevenueByRestaurant(@Param("restaurantId") Long restaurantId);

	// Total revenue for a restaurant on a specific date
	@Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.restaurantId = :restaurantId AND o.date = :date")
	Double getRevenueByRestaurantAndDate(@Param("restaurantId") Long restaurantId, @Param("date") LocalDate date);

	@Query(value = "SELECT o.id AS order_id, u.name AS customer_name, o.date AS order_date, o.total_amount "
			+ "FROM orders o JOIN users u ON o.user_id = u.id "
			+ "WHERE o.restaurant_id = ?1 ORDER BY o.date DESC LIMIT 3", nativeQuery = true)
	List<Object[]> getRecentOrdersByRestaurantId(Long restaurantId);

	@Query(value = "SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount " + "FROM orders o "
			+ "JOIN users u ON o.user_id = u.id " + "JOIN order_item oi ON o.id = oi.order_id "
			+ "JOIN food_items fi ON oi.item_id = fi.id " + "WHERE o.restaurant_id = ?1 "
			+ "ORDER BY o.date DESC", nativeQuery = true)
	List<Object[]> getOrderDetailsByRestaurantId(Long restaurantId);

	@NativeQuery("SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND r.id = ?1")
	List<Object[]> getViewOrdersDetailsForRestaurant(Long restaurantID);

	@NativeQuery("SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND r.id = ?1")
	List<Object[]> getViewOrdersDetailsByRestaurantID(Long restaurantID);

	@Query(
			  value = "SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount " +
			          "FROM orders o, order_items oi, users u, food_items fi, restaurants r " +
			          "WHERE o.user_id = u.id " +
			          "AND o.id = oi.order_id " +
			          "AND oi.item_id = fi.id " +
			          "AND o.restaurant_id = r.id " +
			          "AND r.id = ?1 " +
			          "AND o.date = ?2",
			  nativeQuery = true
			)
			List<Object[]> getViewOrdersDetailsByRestaurantIDAndOrderDate(Long restaurantID, LocalDate orderDate);

}
