package com.capgemini.food_app.model;

import java.time.LocalDate;
import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private Long restaurantId;
	
	private LocalDate date;
	
	private Double totalAmount;

	public Order() {

	}

	public Order(Long id, LocalDate date, Double totalAmount) {
		super();
		this.id = id;
		this.date = date;
		this.totalAmount = totalAmount;
	}

	public Order(Long id, Long userId, Long restaurantId, LocalDate date, Double totalAmount) {
		super();
		this.id = id;
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.date = date;
		this.totalAmount = totalAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", totalAmount=" + totalAmount + "]";
	}

}
