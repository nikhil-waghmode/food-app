package com.capgemini.food_app.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
//edited

@Entity
@Table(name = "orders")
public class Order {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "User ID must not be null")
	private Long userId;
	@NotNull(message = "Restaurant ID must not be null")
	private Long restaurantId;
	
	@NotNull(message = "Order date must not be null")
    @PastOrPresent(message = "Order date cannot be in the future")
	private LocalDate date;
	
	@NotNull(message = "Total amount must not be null")
	@DecimalMin(value = "0.0", inclusive = true, message = "Total amount must be zero or greater")
	private Double totalAmount;

	public Order() {

	}

	public Order(LocalDate date, Double totalAmount) {
		super();
		this.date = date;
		this.totalAmount = totalAmount;
	}

	public Order(Long userId, Long restaurantId, LocalDate date, Double totalAmount) {
		super();
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
