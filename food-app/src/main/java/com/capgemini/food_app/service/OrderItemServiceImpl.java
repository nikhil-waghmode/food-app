package com.capgemini.food_app.service;

import com.capgemini.food_app.exception.OrderItemNotFoundException;
import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	public List<OrderItem> getAllOrderItems() {
		return orderItemRepository.findAll();
	}

	@Override
	public OrderItem getOrderItemById(Long id) {
		return orderItemRepository.findById(id)
				.orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with id:" + id));
	}

	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	@Override
	public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
		orderItem.setId(id);
		return orderItemRepository.save(orderItem);
	}

	@Override
	public OrderItem patchOrderItem(Long id, OrderItem partialOrderItem) {
		return orderItemRepository.findById(id).map(existing -> {
			if (partialOrderItem.getOrderId() != null) {
				existing.setOrderId(partialOrderItem.getOrderId());
			}
			if (partialOrderItem.getItemId() != null) {
				existing.setItemId(partialOrderItem.getItemId());
			}
			if (partialOrderItem.getQuantity() != null) {
				existing.setQuantity(partialOrderItem.getQuantity());
			}
			return orderItemRepository.save(existing);
		}).orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found"));
	}

	@Override
	public void deleteOrderItem(Long id) {
		orderItemRepository.deleteById(id);
	}
}
