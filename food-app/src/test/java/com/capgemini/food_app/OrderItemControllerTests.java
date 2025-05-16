package com.capgemini.food_app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.capgemini.food_app.controller.OrderItemController;
import com.capgemini.food_app.exception.OrderItemNotFoundException;
import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.service.OrderItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OrderItemControllerTests {

	@Mock
	private OrderItemService service;

	@InjectMocks
	private OrderItemController controller;

	private OrderItem sampleOrderItem;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		sampleOrderItem = new OrderItem();
		sampleOrderItem.setId(1L);
		sampleOrderItem.setOrderId(100L);
		sampleOrderItem.setItemId(200L);
		sampleOrderItem.setQuantity(3);
	}

	@Test
	void testGetAllOrderItems() {
		List<OrderItem> items = Arrays.asList(sampleOrderItem);
		when(service.getAllOrderItems()).thenReturn(items);

		ResponseEntity<List<OrderItem>> response = controller.getAllOrderItems();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().size());
		verify(service).getAllOrderItems();
	}

	@Test
	void testGetOrderItemById() {
		when(service.getOrderItemById(1L)).thenReturn(sampleOrderItem);

		ResponseEntity<OrderItem> response = controller.getOrderItemById(1L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(sampleOrderItem, response.getBody());
		verify(service).getOrderItemById(1L);
	}

	@Test
	void testGetOrderItemById_NotFound() {
		when(service.getOrderItemById(99L)).thenThrow(new OrderItemNotFoundException("OrderItem not found"));

		ResponseEntity<OrderItem> response = controller.getOrderItemById(99L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
		verify(service).getOrderItemById(99L);
	}

	@Test
	void testAddOrderItem() {
		when(service.createOrderItem(any(OrderItem.class))).thenReturn(sampleOrderItem);

		ResponseEntity<OrderItem> response = controller.addOrderItem(sampleOrderItem);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(sampleOrderItem, response.getBody());
		assertTrue(response.getHeaders().getLocation().toString().contains("/api/orderitems/"));
		verify(service).createOrderItem(sampleOrderItem);
	}

	@Test
	void testUpdateOrderItem_Success() {
		when(service.updateOrderItem(eq(1L), any(OrderItem.class))).thenReturn(sampleOrderItem);

		ResponseEntity<OrderItem> response = controller.updateOrderItem(1L, sampleOrderItem);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(sampleOrderItem, response.getBody());
		verify(service).updateOrderItem(1L, sampleOrderItem);
	}

	@Test
	void testPatchOrderItem() {
		when(service.patchOrderItem(eq(1L), any(OrderItem.class))).thenReturn(sampleOrderItem);

		ResponseEntity<OrderItem> response = controller.patchOrderItem(1L, sampleOrderItem);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(sampleOrderItem, response.getBody());
		verify(service).patchOrderItem(1L, sampleOrderItem);
	}

	@Test
	void testPatchOrderItem_NotFound() {
		when(service.patchOrderItem(eq(99L), any(OrderItem.class)))
				.thenThrow(new OrderItemNotFoundException("OrderItem not found"));

		assertThrows(RuntimeException.class, () -> controller.patchOrderItem(99L, sampleOrderItem));

		verify(service).patchOrderItem(99L, sampleOrderItem);
	}

	@Test
	void testDeleteOrderItem() {
		doNothing().when(service).deleteOrderItem(1L);

		ResponseEntity<Void> response = controller.deleteOrderItem(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service).deleteOrderItem(1L);
	}
}
