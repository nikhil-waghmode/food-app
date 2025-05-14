package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.food_app.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	@Query(value = "SELECT item_id, SUM(quantity) as total_quantity FROM order_item GROUP BY item_id ORDER BY total_quantity DESC", nativeQuery = true)
	List<Object[]> findTopOrderedItems();
}
