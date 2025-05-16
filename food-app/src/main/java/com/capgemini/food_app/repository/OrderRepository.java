package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	boolean existsById(Long id);

	@Query(value = "SELECT * FROM orders ORDER BY date DESC LIMIT 3", nativeQuery = true)
	List<Order> findTop3ByOrderByDateDesc();

}
