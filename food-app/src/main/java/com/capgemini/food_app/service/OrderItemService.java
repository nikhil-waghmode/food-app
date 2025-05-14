package com.capgemini.food_app.service;

import com.capgemini.food_app.model.OrderItem;
import java.util.List;

public interface OrderItemService {
	List<OrderItem> getAllOrderItems();

	OrderItem getOrderItemById(Long id);

	OrderItem createOrderItem(OrderItem orderItem);

	OrderItem updateOrderItem(Long id, OrderItem orderItem);

	OrderItem patchOrderItem(Long id, OrderItem partialOrderItem);

	void deleteOrderItem(Long id);
}
