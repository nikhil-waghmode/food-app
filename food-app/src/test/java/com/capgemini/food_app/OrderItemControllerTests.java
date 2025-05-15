package com.capgemini.food_app;

import com.capgemini.food_app.controller.OrderItemController;
import com.capgemini.food_app.exception.OrderItemNotFoundException;
import com.capgemini.food_app.model.OrderItem;
import com.capgemini.food_app.service.OrderItemService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderItemController.class)
public class OrderItemControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OrderItemService service;

	private OrderItem sampleOrderItem;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		sampleOrderItem = new OrderItem();
		sampleOrderItem.setId(1L);
		sampleOrderItem.setOrderId(100L);
		sampleOrderItem.setItemId(200L);
		sampleOrderItem.setQuantity(3);
	}

	@Test
	void testGetAllOrderItems() throws Exception {
		when(service.getAllOrderItems()).thenReturn(Collections.singletonList(sampleOrderItem));

		mockMvc.perform(get("/api/orderitems")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1L));
	}

	@Test
	void testGetOrderItemById() throws Exception {
		when(service.getOrderItemById(1L)).thenReturn(sampleOrderItem);

		mockMvc.perform(get("/api/orderitems/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L));
	}

	@Test
	void testGetOrderItemById_NotFound() throws Exception {
		when(service.getOrderItemById(99L)).thenThrow(new OrderItemNotFoundException("OrderItem not found"));

		mockMvc.perform(get("/api/orderitems/99")).andExpect(status().isNotFound());
	}

	@Test
	void testAddOrderItem() throws Exception {
		when(service.createOrderItem(any(OrderItem.class))).thenReturn(sampleOrderItem);

		mockMvc.perform(post("/api/orderitems").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleOrderItem))).andExpect(status().isCreated())
				.andExpect(header().string("Location", "/api/orderitems/1")).andExpect(jsonPath("$.id").value(1L));
	}

	@Test
	void testUpdateOrderItem() throws Exception {
		when(service.updateOrderItem(eq(1L), any(OrderItem.class))).thenReturn(sampleOrderItem);

		mockMvc.perform(put("/api/orderitems/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleOrderItem))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L));
	}

	@Test
	void testUpdateOrderItem_NotFound() throws Exception {
		when(service.updateOrderItem(eq(99L), any(OrderItem.class))).thenThrow(new OrderItemNotFoundException("OrderItem not found"));

		mockMvc.perform(put("/api/orderitems/99").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(sampleOrderItem)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testPatchOrderItem() throws Exception {
		when(service.patchOrderItem(eq(1L), any(OrderItem.class))).thenReturn(sampleOrderItem);

		mockMvc.perform(patch("/api/orderitems/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(sampleOrderItem))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L));
	}

	@Test
	void testDeleteOrderItem() throws Exception {
		doNothing().when(service).deleteOrderItem(1L);

		mockMvc.perform(delete("/api/orderitems/1")).andExpect(status().isNoContent());
	}
}
