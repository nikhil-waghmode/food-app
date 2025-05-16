package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;
import java.util.List;

// edited
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	boolean existsById(Long id);

//	updated on 16
	@NativeQuery("SELECT o.id, order_date, fi.name,fi.price, oi.quantity, total_amount, r.name FROM orders o, order_items oi, users u, food_items fi, restaurants r WHERE o.user_id = u.id AND o.id = oi.order_id AND oi.item_id = fi.id AND o.restaurant_id = r.id AND u.id = ?1")
	List<Object[]> getMyOrdersDetailsForCustomer(Long userID);
}
