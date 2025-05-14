package com.capgemini.food_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.food_app.model.Order;
// edited
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
