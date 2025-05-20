package com.capgemini.food_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@NotNull(message = "Order ID must not be null")
	private Long orderId;
	@NotNull(message = "Item ID must not be null")
	private Long itemId;
	@NotNull(message = "Quantity must not be null")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

	public OrderItem() {
	}

	public OrderItem(Long orderId, Long itemId, Integer quantity) {
		super();
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public OrderItem(Integer quantity) {
		super();
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", orderId=" + orderId + ", itemId=" + itemId + ", quantity=" + quantity + "]";
	}

}
