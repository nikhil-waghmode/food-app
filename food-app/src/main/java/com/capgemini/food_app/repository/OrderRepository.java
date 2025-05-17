package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
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



//	updated on 16
//	@NativeQuery("SELECT o.id, order_date, fi.name,fi.price, oi.quantity, total_amount, r.name FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND u.id = ?1")
	@Query(value = "SELECT o.id, o.date, fi.name, fi.price, oi.quantity, o.total_amount, r.name " +
            "FROM orders o " +
            "JOIN users u ON o.user_id = u.id " +
            "JOIN order_items oi ON o.id = oi.order_id " +
            "JOIN food_items fi ON oi.item_id = fi.id " +
            "JOIN restaurants r ON o.restaurant_id = r.id " +
            "WHERE u.id = ?1", nativeQuery = true)
	List<Object[]> getMyOrdersDetailsForCustomer(Long userID);
}
