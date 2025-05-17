package com.capgemini.food_app.dto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * SELECT o.id as order_id, u.name as customer, u.location as customerLocation,
 * order_date, fi.name as item_name, oi.quantity as item_quantity, total_amount
 * FROM orders o, order_item oi, user u, food_item fi, restaurant r where
 * o.userid = u.id and o.id = oi.orderid and oi.itemid = fi.id and
 * o.restaurantid = r.id;
 */
public class OrderDTO {

	Long orderID;
	String customerName;
	String customerLocation;
	LocalDate orderDate;
	Double totalAmount;
	List<String> items = new ArrayList<>(); // item names + quantities
	String restaurantID;
	Integer itemPrice;

	public OrderDTO() {
		super();
	}

	public OrderDTO(Long orderID, String customerName, String customerLocation, LocalDate orderDate, Double totalAmount,
			List<String> items, String restaurantID, Integer itemPrice) {
		super();
		this.orderID = orderID;
		this.customerName = customerName;
		this.customerLocation = customerLocation;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.items = items;
		this.restaurantID = restaurantID;
		this.itemPrice = itemPrice;
	}

	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public String getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}

	public Integer getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderID=" + orderID + ", customerName=" + customerName + ", customerLocation="
				+ customerLocation + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount + ", items=" + items
				+ ", restaurantID=" + restaurantID + ", itemPrice=" + itemPrice + "]";
	}

}