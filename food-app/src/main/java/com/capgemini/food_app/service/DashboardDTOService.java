package com.capgemini.food_app.service;

import java.util.List;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.model.Order;

public interface DashboardDTOService {

	List<Order> getTop3OrdersByDateDesc();
	
	FoodItem getMostOrderedFoodItem();

	FoodItem getLeastOrderedFoodItem();
	
	int totalResaurants();
	
	int totalFoodItems();
	
	int totalOrders();
	
	int totalUsers();
	
	int totalCustomers();
	
	int totalOwners();

}
