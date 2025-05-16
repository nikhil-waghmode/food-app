package com.capgemini.food_app;


import com.capgemini.food_app.model.Order;
import com.capgemini.food_app.repository.OrderRepository;
import com.capgemini.food_app.service.OrderServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleOrder = new Order();
        sampleOrder.setId(1L);
        sampleOrder.setUserId(1L);
        sampleOrder.setRestaurantId(2L);
        sampleOrder.setDate(LocalDate.now());
        sampleOrder.setTotalAmount(250.0);
    }

    @Test
    @DisplayName("Create Order - Should return saved order")
    void testCreateOrder() {
        when(orderRepository.save(sampleOrder)).thenReturn(sampleOrder);

        Order created = orderService.createOrder(sampleOrder);

        assertEquals(sampleOrder, created);
        verify(orderRepository).save(sampleOrder);
    }

    @Test
    @DisplayName("Get Order By ID - Found should return order")
    void testGetOrderById_Found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        Order result = orderService.getOrderById(1L);

        assertEquals(sampleOrder, result);
        verify(orderRepository).findById(1L);
    }

    @Test
    @DisplayName("Get Order By ID - Not found should throw exception")
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.getOrderById(1L));

        assertTrue(ex.getMessage().contains("Order not found"));
    }

    @Test
    @DisplayName("Get All Orders - Should return list of orders")
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(sampleOrder);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    @DisplayName("Update Order - Success should update and return order")
    void testUpdateOrder_Success() {
        Order updatedOrder = new Order();
        updatedOrder.setUserId(10L);
        updatedOrder.setRestaurantId(20L);
        updatedOrder.setDate(LocalDate.now());
        updatedOrder.setTotalAmount(999.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1L, updatedOrder);

        assertEquals(updatedOrder.getUserId(), result.getUserId());
        assertEquals(updatedOrder.getTotalAmount(), result.getTotalAmount());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(sampleOrder); // saves after mutating existing object
    }

    @Test
    @DisplayName("Update Order - Not found should throw exception")
    void testUpdateOrder_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.updateOrder(1L, sampleOrder));

        assertTrue(ex.getMessage().contains("Order not found"));
    }

    @Test
    @DisplayName("Patch Order - Partial update should modify specific fields")
    void testPatchOrder_PartialUpdate() {
        Order patch = new Order();
        patch.setTotalAmount(300.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        Order result = orderService.patchOrder(1L, patch);

        assertEquals(300.0, result.getTotalAmount());
        verify(orderRepository).save(sampleOrder);
    }

    @Test
    @DisplayName("Patch Order - Not found should throw exception")
    void testPatchOrder_NotFound() {
        Order patch = new Order();
        patch.setTotalAmount(300.0);

        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.patchOrder(1L, patch));

        assertTrue(ex.getMessage().contains("Order not found"));
    }
    
    @Test
    @DisplayName("Delete Order - Should delete successfully")
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }
    
    @Test
    @DisplayName("Delete Order - Should throw exception if deletion fails")
    void testDeleteOrder_Exception() {
        doThrow(new RuntimeException("Deletion failed")).when(orderRepository).deleteById(1L);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> orderService.deleteOrder(1L));

        assertEquals("Deletion failed", ex.getMessage());
        verify(orderRepository).deleteById(1L);
    }
}
