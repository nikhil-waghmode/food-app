package com.capgemini.food_app.service;

import com.capgemini.food_app.dto.OrderDTO;
import com.capgemini.food_app.model.Order;

import java.time.LocalDate;
import java.util.List;

//edited
public interface OrderService {

	Order createOrder(Order order);

	Order getOrderById(Long id);

	List<Order> getAllOrders();

	Order updateOrder(Long id, Order updatedOrder);

	Order patchOrder(Long id, Order order);

	boolean orderExists(Long orderId);
	
	List<OrderDTO> getMyOrdersDetailsForCustomer(Long userID);

	void deleteOrder(Long id);

	
	List<Order> getTop3OrdersByDateDesc();


	List<Object[]> getRevenueByCategoryForAdmin();

	List<Object[]> getOrdersPerWeek();

	List<Object[]> getRevenuePerWeek();

	List<Object[]> getRevenuePerMonth();

	List<Order> getOrdersByRestaurantId(Long restaurantId);

	Long getOrderCountByRestaurant(Long restaurantId);

	Long getOrderCountByRestaurantAndDate(Long restaurantId, LocalDate date);

	Double getTotalRevenueByRestaurant(Long restaurantId);

	Double getRevenueByRestaurantAndDate(Long restaurantId, LocalDate date);

	List<OrderDTO> getRecentOrdersByRestaurantId(Long restaurantId);

	public List<OrderDTO> getViewOrdersDetailsForRestaurant(Long restaurantId, LocalDate date);

	List<Object[]> dataForOrdersPerWeekChart(Long restaurantID);

	List<Object[]> dataForRevenuePerWeekChart(Long restaurantID);

	List<Object[]> dataForRevenuePerMonthChart(Long restaurantID);

	List<Object[]> getRevenueByCategory(Long restaurantID);
}
