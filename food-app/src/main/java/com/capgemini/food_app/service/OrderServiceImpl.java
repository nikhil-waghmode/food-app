package com.capgemini.food_app.service;

import com.capgemini.food_app.dto.OrderDTO;
import com.capgemini.food_app.exception.OrderNotFoundException;
import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public boolean orderExists(Long orderId) {
        return orderRepository.existsById(orderId);
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
    @Override
	public List<OrderDTO> getMyOrdersDetailsForCustomer(Long customerID) {

		List<Object[]> rows = orderRepository.getMyOrdersDetailsForCustomer(customerID);
		Map<Long, OrderDTO> orderMap = new LinkedHashMap<>(); // preserves insertion order

		for (Object[] row : rows) {
			Long orderID = ((Number) row[0]).longValue();
//			String customerName = (String) row[1];
//			String customerLocation = (String) row[2];
			LocalDate orderDate = ((java.sql.Date) row[1]).toLocalDate();
			String itemName = (String) row[2];
			Integer itemPrice = ((Number) row[3]).intValue();
			Integer quantity = ((Number) row[4]).intValue();
			Long totalAmount = ((Number) row[5]).longValue();
			String restaurantID = (String) row[6];
			OrderDTO dto = orderMap.get(orderID);

			if (dto == null) {
				dto = new OrderDTO();
				dto.setOrderID(orderID);
//				dto.setCustomerName(customerName);
//				dto.setCustomerLocation(customerLocation);
				dto.setOrderDate(orderDate);
				dto.setItemPrice(itemPrice);
				dto.setTotalAmount(totalAmount);
				dto.setRestaurantID(restaurantID);
				orderMap.put(orderID, dto);
			}

			// Add item to list
			dto.getItems().add(String.format("%s (x%d)", itemName, quantity));
		}
//		System.out.println(orderMap.values());

		return new ArrayList<>(orderMap.values());
	}
}
