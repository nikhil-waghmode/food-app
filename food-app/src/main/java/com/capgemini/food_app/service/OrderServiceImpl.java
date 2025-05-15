package com.capgemini.food_app.service;

import com.capgemini.food_app.exception.OrderNotFoundException;
import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//edited
@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
    	this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order not found with id: "+id ));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        Order order =  orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
        order.setUserId(updatedOrder.getUserId());
        order.setRestaurantId(updatedOrder.getRestaurantId());
        order.setDate(updatedOrder.getDate());
        order.setTotalAmount(updatedOrder.getTotalAmount());
        return orderRepository.save(order);
    }
     
    @Override
	public Order patchOrder(Long id, Order newOrder) {
		Order oldOrder = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
		if (newOrder.getUserId() != null) {
			oldOrder.setUserId(newOrder.getUserId());
		}
		if (newOrder.getRestaurantId() != null) {
			oldOrder.setRestaurantId(newOrder.getRestaurantId());
		}
		if (newOrder.getDate() != null) {
			oldOrder.setDate(newOrder.getDate());
		}
		if (newOrder.getTotalAmount() != null) {
			oldOrder.setTotalAmount(newOrder.getTotalAmount());
		}
		return orderRepository.save(oldOrder);

	}

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
