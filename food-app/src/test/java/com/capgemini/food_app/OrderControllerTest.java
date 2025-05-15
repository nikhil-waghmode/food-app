package com.capgemini.food_app;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.capgemini.food_app.controller.OrderController;
import com.capgemini.food_app.exception.OrderNotFoundException;
import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.service.OrderService;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleOrder = new Order();
        sampleOrder.setUserId(1L);
        sampleOrder.setRestaurantId(2L);
        sampleOrder.setDate(LocalDate.now());
        sampleOrder.setTotalAmount(500.0);
    }

    @Test
    @DisplayName("Create Order - Should return CREATED status and order data")
    void testCreateOrder() {
        when(orderService.createOrder(any(Order.class))).thenReturn(sampleOrder);

        ResponseEntity<Order> response = orderController.createOrder(sampleOrder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleOrder, response.getBody());
        assertTrue(response.getHeaders().getLocation().toString().contains("/api/orders/"));
        verify(orderService, times(1)).createOrder(sampleOrder);
    }

    @Test
    @DisplayName("Get Order By ID - Should return OK and the order data")
    void testGetOrderById() {
        when(orderService.getOrderById(1L)).thenReturn(sampleOrder);

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleOrder, response.getBody());
        verify(orderService).getOrderById(1L);
    }

    @Test
    @DisplayName("Get All Orders - Should return OK and list of orders")
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(sampleOrder);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(orderService).getAllOrders();
    }

    @Test
    @DisplayName("Update Order - Success case should return OK and updated order")
    void testUpdateOrder_Success() {
        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(sampleOrder);

        ResponseEntity<Order> response = orderController.updateOrder(1L, sampleOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleOrder, response.getBody());
        verify(orderService).updateOrder(1L, sampleOrder);
    }

    @Test
    @DisplayName("Update Order - Not found should return NOT_FOUND and null body")
    void testUpdateOrder_NotFound() {
        when(orderService.updateOrder(eq(1L), any(Order.class))).thenThrow(new OrderNotFoundException("Not found"));

        ResponseEntity<Order> response = orderController.updateOrder(1L, sampleOrder);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(orderService).updateOrder(1L, sampleOrder);
    }

    @Test
    @DisplayName("Patch Order - Should return OK and patched order")
    void testPatchOrder() {
        when(orderService.patchOrder(eq(1L), any(Order.class))).thenReturn(sampleOrder);

        ResponseEntity<Order> response = orderController.patchOrder(1L, sampleOrder);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleOrder, response.getBody());
        verify(orderService).patchOrder(1L, sampleOrder);
    }

    @Test
    @DisplayName("Delete Order - Should return NO_CONTENT status")
    void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder(1L);

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService).deleteOrder(1L);
    }
    @Test
    @DisplayName("Get Order By ID - Should throw RuntimeException when not found")
    void testGetOrderById_Exception() {
        when(orderService.getOrderById(1L)).thenThrow(new OrderNotFoundException("Order not found"));

        assertThrows(RuntimeException.class, () -> orderController.getOrderById(1L));
        verify(orderService).getOrderById(1L);
    }
    
    @Test
    @DisplayName("Patch Order - Should throw RuntimeException on failure")
    void testPatchOrder_Exception() {
        when(orderService.patchOrder(eq(1L), any(Order.class))).thenThrow(new OrderNotFoundException("Patch failed"));

        assertThrows(RuntimeException.class, () -> orderController.patchOrder(1L, sampleOrder));
        verify(orderService).patchOrder(1L, sampleOrder);
    }


}
