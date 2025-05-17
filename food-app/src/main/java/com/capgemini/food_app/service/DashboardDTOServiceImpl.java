package com.capgemini.food_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.FoodItemRepository;
import com.capgemini.food_app.repository.OrderRepository;
import com.capgemini.food_app.repository.RestaurantRepository;
import com.capgemini.food_app.repository.UserRepository;

@Service
public class DashboardDTOServiceImpl implements DashboardDTOService {

	OrderRepository orderRepository;
	FoodItemRepository foodItemRepo;
	RestaurantRepository restaurantRepository;
	UserRepository userRepository;
	
	@Autowired
	public DashboardDTOServiceImpl(OrderRepository orderRepository,UserRepository userRepository, FoodItemRepository foodItemRepo, RestaurantRepository restaurantRepository) {
		this.orderRepository = orderRepository;
		this.foodItemRepo = foodItemRepo;
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
	}
	
	@Override
    public List<Order> getTop3OrdersByDateDesc() {
        return orderRepository.findTop3ByOrderByDateDesc();
    }
	
	@Override
	public FoodItem getMostOrderedFoodItem() {
		FoodItem foodItems = foodItemRepo.findMostOrderedFoodItem();
		return foodItems;
	}
	
	@Override
	public FoodItem getLeastOrderedFoodItem() {
		FoodItem foodItems = foodItemRepo.findLeastOrderedFoodItem();
		return foodItems;
	}
	
	@Override
	public int totalResaurants() {
		int total = (int)restaurantRepository.count();
		return total;
	}
	
	@Override
	public int totalFoodItems() {
		int total = (int)foodItemRepo.count();
		return total;
	}
	
	@Override
	public int totalOrders() {
		int total = (int)orderRepository.count();
		return total;
	}
	
	@Override
	public int totalUsers() {
		int total = (int)userRepository.count();
		return total;
	}
	
	@Override
	public int totalCustomers() {
		int total = userRepository.totalCustomer();
	    return  total;
	}
	
	@Override
	public int totalOwners() {
		int total = userRepository.totalOwner();
	    return  total;
	}
	
	
}
