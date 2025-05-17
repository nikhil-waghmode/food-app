package com.capgemini.food_app.dto;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.food_app.model.Order;

public class DashboardDTO {

	List<Order> recentOrders = new ArrayList<>();
	int totalRestaurants;
	int totalFoodItems;
	int totalOrders;
	int totalCustomers;
	int totalOwners;
	int totalUsers;
	String bestSelling;
	String leastSelling;

	public DashboardDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DashboardDTO(List<Order> recentOrders, int totalRestaurants, int totalFoodItems, int totalOrders,
			int totalCustomers, int totalOwners, int totalUsers, String bestSelling, String leastSelling) {
		super();
		this.recentOrders = recentOrders;
		this.totalRestaurants = totalRestaurants;
		this.totalFoodItems = totalFoodItems;
		this.totalOrders = totalOrders;
		this.totalCustomers = totalCustomers;
		this.totalOwners = totalOwners;
		this.totalUsers = totalUsers;
		this.bestSelling = bestSelling;
		this.leastSelling = leastSelling;
	}

	public List<Order> getRecentOrders() {
		return recentOrders;
	}

	public void setRecentOrders(List<Order> recentOrders) {
		this.recentOrders = recentOrders;
	}

	public int getTotalRestaurants() {
		return totalRestaurants;
	}

	public void setTotalRestaurants(int totalRestaurants) {
		this.totalRestaurants = totalRestaurants;
	}

	public int getTotalFoodItems() {
		return totalFoodItems;
	}

	public void setTotalFoodItems(int totalFoodItems) {
		this.totalFoodItems = totalFoodItems;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	public int getTotalCustomers() {
		return totalCustomers;
	}

	public void setTotalCustomers(int totalCustomers) {
		this.totalCustomers = totalCustomers;
	}

	public int getTotalOwners() {
		return totalOwners;
	}

	public void setTotalOwners(int totalOwners) {
		this.totalOwners = totalOwners;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public String getBestSelling() {
		return bestSelling;
	}

	public void setBestSelling(String bestSelling) {
		this.bestSelling = bestSelling;
	}

	public String getLeastSelling() {
		return leastSelling;
	}

	public void setLeastSelling(String leastSelling) {
		this.leastSelling = leastSelling;
	}

}
