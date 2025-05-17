package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
