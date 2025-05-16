package com.capgemini.food_app.controller;

import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/orderitems")
@Slf4j
public class OrderItemController {

	private final OrderItemService service;

	@Autowired
	public OrderItemController(OrderItemService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<OrderItem>> getAllOrderItems() {
		log.info("Fetching all order items");
		List<OrderItem> items = service.getAllOrderItems();
		log.debug("Total order items fetched: {}", items.size());
		return ResponseEntity.status(HttpStatus.OK).body(items);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
		log.info("Fetching order item with ID: {}", id);
		try {
			OrderItem item = service.getOrderItemById(id);
			log.info("Fetched order item: {}", item);
			return ResponseEntity.ok(item);
		} catch (RuntimeException e) {
			log.warn("Order item with ID {} not found", id);
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<OrderItem> addOrderItem(@RequestBody OrderItem orderItem) {
		log.info("Adding new order item: {}", orderItem);
		OrderItem createdItem = service.createOrderItem(orderItem);
		log.info("Created order item: {}", createdItem);
		return ResponseEntity.status(HttpStatus.CREATED)
				.location(URI.create("/api/orderitems/" + createdItem.getId()))
				.body(createdItem);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
		log.info("Updating order item with ID: {}", id);
		try {
			OrderItem updated = service.updateOrderItem(id, orderItem);
			log.info("Updated order item: {}", updated);
			return ResponseEntity.ok(updated);
		} catch (RuntimeException e) {
			log.error("Error updating order item with ID {}: {}", id, e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<OrderItem> patchOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem) {
		log.trace("Patching order item with ID: {}", id);
		OrderItem patched = service.patchOrderItem(id, orderItem);
		log.info("Patched order item: {}", patched);
		return ResponseEntity.ok(patched);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
		log.warn("Deleting order item with ID: {}", id);
		try {
			service.deleteOrderItem(id);
			log.info("Deleted order item with ID: {}", id);
			return ResponseEntity.noContent().build();
		} catch (RuntimeException e) {
			log.error("Error deleting order item with ID {}: {}", id, e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
//	@GetMapping("/best")
//    public ResponseEntity<Long> getItemIdWithMaxCount() {
//        Long itemId = service.getItemIdWithMaxCount();
//        return itemId != null ? ResponseEntity.ok(itemId) : ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/least")
//    public ResponseEntity<Long> getItemIdWithMinCount() {
//        Long itemId = service.getItemIdWithMinCount();
//        return itemId != null ? ResponseEntity.ok(itemId) : ResponseEntity.noContent().build();
//    }
}
