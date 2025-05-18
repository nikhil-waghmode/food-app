package com.capgemini.food_app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;



import java.time.LocalDate;

import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	boolean existsById(Long id);


	
	@Query(value = "SELECT * FROM orders ORDER BY date DESC LIMIT 3", nativeQuery = true)
	List<Order> findTop3ByOrderByDateDesc();

	@Query(value = "SELECT DATE_FORMAT(date, '%Y-%u') AS week, COUNT(*) AS orders "
			+ "FROM orders GROUP BY week ORDER BY week", nativeQuery = true)
	List<Object[]> getOrdersPerWeek();

	@Query(value = "SELECT DATE_FORMAT(date, '%Y-%u') AS week, SUM(total_amount) AS revenue "
			+ "FROM orders GROUP BY week ORDER BY week", nativeQuery = true)
	List<Object[]> getRevenuePerWeek();

	@Query(value = "SELECT DATE_FORMAT(date, '%Y-%m') AS month, SUM(total_amount) AS revenue "
			+ "FROM orders GROUP BY month ORDER BY month", nativeQuery = true)
	List<Object[]> getRevenuePerMonth();

	@Query(value = "SELECT fi.category, SUM(fi.price * oi.quantity) AS revenue FROM order_items oi, food_items fi, orders o WHERE oi.item_id = fi.id AND oi.order_id = o.id GROUP BY fi.category", nativeQuery = true)
	List<Object[]> getRevenueByCategoryForAdmin();

	@Query(value = "SELECT o.id, o.date, fi.name, fi.price, oi.quantity, o.total_amount, r.name " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.id " +
            "JOIN order_items oi ON o.id = oi.order_id " +
            "JOIN food_items fi ON oi.item_id = fi.id " +
            "JOIN restaurants r ON o.restaurant_id = r.id " +
            "WHERE u.id = ?1", nativeQuery = true)
	List<Object[]> getMyOrdersDetailsForCustomer(Long userID);

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
			+ "JOIN users u ON o.user_id = u.id " + "JOIN order_items oi ON o.id = oi.order_id "
			+ "JOIN food_items fi ON oi.item_id = fi.id " + "WHERE o.restaurant_id = ?1 "
			+ "ORDER BY o.date DESC", nativeQuery = true)
	List<Object[]> getOrderDetailsByRestaurantId(Long restaurantId);

	@NativeQuery("SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND r.id = ?1")
	List<Object[]> getViewOrdersDetailsForRestaurant(Long restaurantID);

	@NativeQuery("SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND r.id = ?1")
	List<Object[]> getViewOrdersDetailsByRestaurantID(Long restaurantID);

	@Query(value = "SELECT o.id, u.name, u.location, o.date, fi.name, oi.quantity, o.total_amount "
			+ "FROM orders o, order_items oi, users u, food_items fi, restaurants r " + "WHERE o.user_id = u.id "
			+ "AND o.id = oi.order_id " + "AND oi.item_id = fi.id " + "AND o.restaurant_id = r.id " + "AND r.id = ?1 "
			+ "AND o.date = ?2", nativeQuery = true)
	List<Object[]> getViewOrdersDetailsByRestaurantIDAndOrderDate(Long restaurantID, LocalDate orderDate);

	@NativeQuery("SELECT fi.category, SUM(fi.price * oi.quantity) AS revenue FROM order_items oi, food_items fi, orders o WHERE oi.item_id = fi.id AND oi.order_id = o.id AND fi.restaurant_id = ?1 GROUP BY fi.category")
	List<Object[]> getRevenueByCategory(Long restaurantID);

	@NativeQuery("select WEEK(date) as Weeks, COUNT(*) AS Orders from orders o WHERE o.restaurant_id=?1 group by Weeks order by Weeks;")
	List<Object[]> dataForOrdersPerWeekChart(Long restaurantID);

	@NativeQuery("select WEEK(date) as Weeks, SUM(o.total_amount) AS Orders from orders o WHERE o.restaurant_id=?1 group by Weeks order by Weeks;")
	List<Object[]> dataForRevenuePerWeekChart(Long restaurantID);

	@NativeQuery("select MONTH(date) as Months, SUM(o.total_amount) AS Orders from orders o WHERE o.restaurant_id=?1 group by Months order by Months;")
	List<Object[]> dataForRevenuePerMonthChart(Long restaurantID);

}
