package com.capgemini.food_app.rest;

import com.capgemini.food_app.exception.OrderItemNotFoundException;
import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.service.OrderItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/orderitems")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('OWNER')")

public class OrderItemController {

	private final OrderItemService service;

	@Autowired
	public OrderItemController(OrderItemService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrderItems());
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.getOrderItemById(id));
	}

	@PostMapping
	public ResponseEntity<OrderItem> addOrderItem(@Valid @RequestBody OrderItem orderItem) {
		OrderItem createdItem = service.createOrderItem(orderItem);
		return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/orderitems/" + createdItem.getId()))
				.body(createdItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItem orderItem) {
		try {
			return ResponseEntity.ok(service.updateOrderItem(id, orderItem));
		} catch (RuntimeException e) {
			throw new OrderItemNotFoundException("Cannot update. OrderItem not found with ID: " + id);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<OrderItem> patchOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItem orderItem) {
		return ResponseEntity.status(HttpStatus.OK).body(service.patchOrderItem(id, orderItem));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
		service.deleteOrderItem(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
