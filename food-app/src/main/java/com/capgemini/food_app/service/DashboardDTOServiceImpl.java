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
		return foodItemRepo.findMostOrderedFoodItem();
	}
	
	@Override
	public FoodItem getLeastOrderedFoodItem() {
		return foodItemRepo.findLeastOrderedFoodItem();
	}
	
	@Override
	public int totalResaurants() {
		return (int)restaurantRepository.count();
	}
	
	@Override
	public int totalFoodItems() {
		return (int)foodItemRepo.count();
	}
	
	@Override
	public int totalOrders() {
		return (int)orderRepository.count();
	}
	
	@Override
	public int totalUsers() {
		return (int)userRepository.count();
	}
	
	@Override
	public int totalCustomers() {
		return userRepository.totalCustomer();
	}
	
	@Override
	public int totalOwners() {
		return userRepository.totalOwner();
	}
	
	
}
