package com.capgemini.food_app.service;

import com.capgemini.food_app.model.Order;
import java.util.List;
//edited
public interface OrderService {
 
	Order createOrder(Order order);

	Order getOrderById(Long id);

	List<Order> getAllOrders();

	Order updateOrder(Long id, Order updatedOrder);

	Order patchOrder(Long id, Order order);

	boolean orderExists(Long orderId);

	void deleteOrder(Long id);
	
	List<Order> getTop3OrdersByDateDesc();
}
