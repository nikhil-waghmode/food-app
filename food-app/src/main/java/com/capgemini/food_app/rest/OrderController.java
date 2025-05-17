package com.capgemini.food_app.rest;

import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.OrderRepository;
import com.capgemini.food_app.service.OrderService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private OrderService orderService;
    private OrderRepository orderRepository;
    
    @Autowired
    public OrderController(OrderService orderService,OrderRepository orderRepository) {
    	this.orderService = orderService;
    	this.orderRepository=orderRepository;
    }


    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/api/orders/" + createdOrder.getId()))
                .body(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    	return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        try {
            Order updated = orderService.updateOrder(id, order);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Order> patchOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.patchOrder(id, order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/restaurant/charts/orders-per-week")
    public List<Object[]> getOrdersPerWeek() {
        return orderRepository.getOrdersPerWeek();
    }
    
    @GetMapping("/restaurant/charts/revenue-per-week")
    public List<Object[]> getRevenuePerWeek() {
        return orderRepository.getRevenuePerWeek();
    }

    @GetMapping("/restaurant/charts/revenue-per-month")
    public List<Object[]> getRevenuePerMonth() {
        return orderRepository.getRevenuePerMonth();
    }
    
    @GetMapping("/restaurant/charts/revenue-by-category")
	public ResponseEntity<List<Object[]>> getRevenueByCategoryForAdmin() {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getRevenueByCategoryForAdmin());
	}
}
