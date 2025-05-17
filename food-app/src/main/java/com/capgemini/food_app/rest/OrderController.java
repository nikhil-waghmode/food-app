package com.capgemini.food_app.rest;

import com.capgemini.food_app.dto.OrderDTO;
import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.OrderRepository;
import com.capgemini.food_app.service.OrderService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
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

//    @Autowired
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }

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


//    @GetMapping("/recents")
//    public ResponseEntity<List<Order>> getRecentOrders() {
//        List<Order> orders = orderService.getTop3OrdersByDateDesc();
//        return ResponseEntity.ok(orders);
//    }
    
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

    @GetMapping("/detail/customer/{customerID}")
	ResponseEntity<List<OrderDTO>> getMyOrdersDetailsForCustomer(@PathVariable Long customerID) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrdersDetailsForCustomer(customerID));
	}
    @GetMapping("/restaurant/count/{restaurantId}")
    public ResponseEntity<Long> getOrderCount(
            @PathVariable Long restaurantId,
            @RequestParam(value = "date", required = false) String date) {
        if (date == null) {
            return ResponseEntity.ok(orderService.getOrderCountByRestaurant(restaurantId));
        } else {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                return ResponseEntity.ok(orderService.getOrderCountByRestaurantAndDate(restaurantId, parsedDate));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(0L);
            }
        }
    }

    @GetMapping("/restaurant/revenue/{restaurantId}")
    public ResponseEntity<Double> getRevenue(
            @PathVariable Long restaurantId,
            @RequestParam(value = "date", required = false) String date) {
        if (date == null) {
            return ResponseEntity.ok(orderService.getTotalRevenueByRestaurant(restaurantId));
        } else {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                return ResponseEntity.ok(orderService.getRevenueByRestaurantAndDate(restaurantId, parsedDate));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(0.0);
            }
        }
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByRestaurant(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/detail/restaurant/{restaurantID}")
    public ResponseEntity<List<OrderDTO>> getViewOrdersDetailsForRestaurant(@PathVariable Long restaurantID,
            @RequestParam(value = "date", required = false) String date) {
        if (date == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderService.getViewOrdersDetailsForRestaurant(restaurantID, null));
        } else if (date.equalsIgnoreCase("today")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(orderService.getViewOrdersDetailsForRestaurant(restaurantID, LocalDate.now()));
        } else {
            try {
                LocalDate parsedDate = LocalDate.parse(date);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(orderService.getViewOrdersDetailsForRestaurant(restaurantID, parsedDate));
            } catch (DateTimeParseException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
            }
        }
    }

    // ✅ New chart-related endpoints from the second file

    @GetMapping("/restaurant/charts/orders-per-week/{restaurantId}")
    public ResponseEntity<List<Object[]>> dataForOrdersPerWeekChart(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.dataForOrdersPerWeekChart(restaurantId));
    }

//    @GetMapping("/restaurant/charts/orders-per-week")
//    public ResponseEntity<List<Object[]>> dataForOrdersPerWeekChartForAdmin() {
//        return ResponseEntity.ok(orderService.dataForOrdersPerWeekChartForAdmin());
//    }

    @GetMapping("/restaurant/charts/revenue-per-week/{restaurantId}")
    public ResponseEntity<List<Object[]>> dataForRevenuePerWeekChart(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.dataForRevenuePerWeekChart(restaurantId));
    }

//    @GetMapping("/restaurant/charts/revenue-per-week")
//    public ResponseEntity<List<Object[]>> dataForRevenuePerWeekChartForAdmin() {
//        return ResponseEntity.ok(orderService.dataForRevenuePerWeekChartForAdmin());
//    }

    @GetMapping("/restaurant/charts/revenue-per-month/{restaurantId}")
    public ResponseEntity<List<Object[]>> dataForRevenuePerMonthChart(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.dataForRevenuePerMonthChart(restaurantId));
    }

//    @GetMapping("/restaurant/charts/revenue-per-month")
//    public ResponseEntity<List<Object[]>> dataForRevenuePerMonthChartForAdmin() {
//        return ResponseEntity.ok(orderService.dataForRevenuePerMonthChartForAdmin());
//    }

    @GetMapping("/restaurant/charts/revenue-by-category/{restaurantId}")
    public ResponseEntity<List<Object[]>> getRevenueByCategory(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getRevenueByCategory(restaurantId));
    }

}
