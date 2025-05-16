package com.capgemini.food_app.controller;

import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.service.OrderService;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        log.info("Creating new order for customer ID: {}", order.getUserId());
        Order createdOrder = orderService.createOrder(order);
        log.info("Created order: {}", createdOrder);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/orders/" + createdOrder.getId()))
                .body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {}", id);
        try {
            Order order = orderService.getOrderById(id);
            log.info("Fetched order: {}", order);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.warn("Order with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderService.getAllOrders();
        log.info("Fetched {} orders", orders.size());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/recents")
    public ResponseEntity<List<Order>> getRecentOrders() {
        List<Order> orders = orderService.getTop3OrdersByDateDesc();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        log.info("Updating order with ID: {}", id);
        try {
            Order updated = orderService.updateOrder(id, order);
            log.info("Updated order: {}", updated);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            log.error("Failed to update order with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable Long id, @RequestBody Order order) {
        log.trace("Patching order with ID: {}", id);
        Order patched = orderService.patchOrder(id, order);
        log.info("Patched order: {}", patched);
        return ResponseEntity.ok(patched);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.warn("Deleting order with ID: {}", id);
        try {
            orderService.deleteOrder(id);
            log.info("Successfully deleted order with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting order with ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
