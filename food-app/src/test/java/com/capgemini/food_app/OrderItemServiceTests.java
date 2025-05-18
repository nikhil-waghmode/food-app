package com.capgemini.food_app;

import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.repository.OrderItemRepository;
import com.capgemini.food_app.service.OrderItemService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderItemServiceTests {

	@MockitoBean
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderItemService orderItemService;

	private OrderItem createSampleOrderItem() {
		OrderItem item = new OrderItem();
		item.setId(1L);
		item.setOrderId(101L);
		item.setItemId(501L);
		item.setQuantity(3);
		return item;
	}

	@Test
	@DisplayName("Should return all order items")
	void shouldReturnAllOrderItems() {
		OrderItem item = createSampleOrderItem();
		when(orderItemRepository.findAll()).thenReturn(Arrays.asList(item));

		List<OrderItem> items = orderItemService.getAllOrderItems();

		assertEquals(1, items.size());
		assertEquals(1L, items.get(0).getId());
		verify(orderItemRepository).findAll();
	}

	@Test
	@DisplayName("Should return order item by ID")
	void shouldReturnOrderItemById() {
		OrderItem item = createSampleOrderItem();
		when(orderItemRepository.findById(1L)).thenReturn(Optional.of(item));

		OrderItem found = orderItemService.getOrderItemById(1L);

		assertNotNull(found);
		assertEquals(1L, found.getId());
		verify(orderItemRepository).findById(1L);
	}

	@Test
	@DisplayName("Should save new order item")
	void shouldCreateOrderItem() {
		OrderItem item = createSampleOrderItem();
		when(orderItemRepository.save(item)).thenReturn(item);

		OrderItem saved = orderItemService.createOrderItem(item);

		assertNotNull(saved);
		assertEquals(1L, saved.getId());
		verify(orderItemRepository).save(item);
	}

	@Test
	@DisplayName("Should update order item")
	void shouldUpdateOrderItem() {
		OrderItem item = createSampleOrderItem();
		item.setQuantity(5);
		when(orderItemRepository.save(item)).thenReturn(item);

		OrderItem updated = orderItemService.updateOrderItem(1L, item);

		assertNotNull(updated);
		assertEquals(5, updated.getQuantity());
		assertEquals(1L, updated.getId());
		verify(orderItemRepository).save(item);
	}

	@Test
	@DisplayName("Should patch order item")
	void shouldPatchOrderItem() {
		OrderItem existing = createSampleOrderItem();
		OrderItem patch = new OrderItem();
		patch.setQuantity(10);

		when(orderItemRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(inv -> inv.getArgument(0));

		OrderItem result = orderItemService.patchOrderItem(1L, patch);

		assertNotNull(result);
		assertEquals(10, result.getQuantity());
		assertEquals(101L, result.getOrderId());
		verify(orderItemRepository).findById(1L);
		verify(orderItemRepository).save(existing);
	}

	@Test
	@DisplayName("Should delete order item by ID")
	void shouldDeleteOrderItem() {
		doNothing().when(orderItemRepository).deleteById(1L);

		orderItemService.deleteOrderItem(1L);

		verify(orderItemRepository).deleteById(1L);
	}
}
