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
		return orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
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
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id " + id));
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
	public List<Order> getOrdersByRestaurantId(Long restaurantId) {
		return orderRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public Long getOrderCountByRestaurant(Long restaurantId) {
		return orderRepository.getOrderCountByRestaurant(restaurantId);
	}

	@Override
	public Long getOrderCountByRestaurantAndDate(Long restaurantId, LocalDate date) {
		return orderRepository.getOrderCountByRestaurantAndDate(restaurantId, date);
	}

	@Override
	public Double getTotalRevenueByRestaurant(Long restaurantId) {
		return orderRepository.getTotalRevenueByRestaurant(restaurantId);
	}

	@Override
	public Double getRevenueByRestaurantAndDate(Long restaurantId, LocalDate date) {
		return orderRepository.getRevenueByRestaurantAndDate(restaurantId, date);
	}

	@Override
	public List<OrderDTO> getRecentOrdersByRestaurantId(Long restaurantId) {
		List<Object[]> rows = orderRepository.getRecentOrdersByRestaurantId(restaurantId);
		List<OrderDTO> dtos = new ArrayList<>();
		for (Object[] row : rows) {
			OrderDTO dto = new OrderDTO();
			dto.setOrderID(((Number) row[0]).longValue());
			dto.setCustomerName((String) row[1]);
			dto.setOrderDate(((java.sql.Date) row[2]).toLocalDate());
			dto.setTotalAmount(((Number) row[3]).doubleValue());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<OrderDTO> getViewOrdersDetailsForRestaurant(Long restaurantId, LocalDate date) {
		List<Object[]> rows;
		// Fetch rows based on date filter
		if (date == null) {
			rows = orderRepository.getViewOrdersDetailsByRestaurantID(restaurantId);
		} else {
			rows = orderRepository.getViewOrdersDetailsByRestaurantIDAndOrderDate(restaurantId, date);
		}

		// Use LinkedHashMap to preserve insertion order and group by orderID
		Map<Long, OrderDTO> orderMap = new LinkedHashMap<>();

		for (Object[] row : rows) {
			Long orderID = ((Number) row[0]).longValue();
			String customerName = (String) row[1];
			String customerLocation = (String) row[2];
			LocalDate orderDate = ((java.sql.Date) row[3]).toLocalDate();
			String itemName = (String) row[4];
			Integer quantity = ((Number) row[5]).intValue();
			Double totalAmount = ((Number) row[6]).doubleValue();

			// Get or create DTO for this order
			OrderDTO dto = orderMap.get(orderID);
			if (dto == null) {
				dto = new OrderDTO();
				dto.setOrderID(orderID);
				dto.setCustomerName(customerName);
				dto.setCustomerLocation(customerLocation);
				dto.setOrderDate(orderDate);
				dto.setTotalAmount(totalAmount);
				orderMap.put(orderID, dto);
			}
			// Add item to items list
			dto.getItems().add(String.format("%s (x%d)", itemName, quantity));
			System.out.println("OrderID: " + orderID + ", Customer: " + customerName + ", Location: " + customerLocation
					+ ", Date: " + orderDate + ", Item: " + itemName + ", Qty: " + quantity + ", Total: "
					+ totalAmount);

		}

		// Return as a list
		return new ArrayList<>(orderMap.values());
	}

	@Override
	public List<Object[]> getRevenueByCategory(Long restaurantID) {
		return orderRepository.getRevenueByCategory(restaurantID);
	}

	@Override
	public List<Object[]> dataForOrdersPerWeekChart(Long restaurantID) {
		// TODO Auto-generated method stub
		return orderRepository.dataForOrdersPerWeekChart(restaurantID);
	}

	@Override
	public List<Object[]> dataForRevenuePerWeekChart(Long restaurantID) {
		// TODO Auto-generated method stub
		return orderRepository.dataForRevenuePerWeekChart(restaurantID);
	}

	@Override
	public List<Object[]> dataForRevenuePerMonthChart(Long restaurantID) {
		// TODO Auto-generated method stub
		return orderRepository.dataForRevenuePerMonthChart(restaurantID);
	}

}
