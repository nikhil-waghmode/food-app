package com.capgemini.food_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;
// edited
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	boolean existsById(Long id);
	List<Order> findByRestaurantId(Long restaurantId);

}
