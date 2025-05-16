package com.capgemini.food_app;

import com.capgemini.food_app.exception.RestaurantNotFoundException;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.controller.RestaurantController;
import com.capgemini.food_app.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private RestaurantService restaurantService;

        @Autowired
        private ObjectMapper objectMapper;

        private Restaurant restaurant;

        @BeforeEach
        void setUp() {
                restaurant = new Restaurant();
                restaurant.setId(1L);
                restaurant.setName("Test Restaurant");
                restaurant.setLocation("Test City");
                restaurant.setContact("1234567890");
                restaurant.setOwnerId(1L);
        }

        @Test
        void testGetAllRestaurants() throws Exception {
                when(restaurantService.getAllRestaurants()).thenReturn(Arrays.asList(restaurant));

                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].name").value("Test Restaurant"));
        }

        @Test
        void testGetRestaurantById_Success() throws Exception {
                when(restaurantService.getRestaurantById(1L)).thenReturn(restaurant);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.contact").value("1234567890"));
        }

        @Test
        void testGetRestaurantById_NotFound() throws Exception {
                when(restaurantService.getRestaurantById(2L))
                                .thenThrow(new RestaurantNotFoundException("Restaurant not found with ID: 2"));

                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/2"))
                                .andExpect(status().isNotFound())
                                .andDo(result -> System.out
                                                .println("GET Response: " + result.getResponse().getContentAsString()))
                                .andExpect(jsonPath("$.message").value("Restaurant not found with ID: 2"));
        }

        @Test
        void testGetRestaurantsByOwner() throws Exception {
                List<Restaurant> restaurants = Arrays.asList(restaurant);
                when(restaurantService.getRestaurantsByOwner(1L)).thenReturn(restaurants);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/owner/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].ownerId").value(1));
        }

        @Test
        void testGetRestaurantCount() throws Exception {
                when(restaurantService.getTotalRestaurantsCount()).thenReturn(5L);

                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/count"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("5"));
        }

        @Test
        void testCreateRestaurant() throws Exception {
                when(restaurantService.createRestaurant(any(), any(), any(), any(), any()))
                                .thenReturn(restaurant);

                mockMvc.perform(MockMvcRequestBuilders.multipart("/api/restaurants")
                                .file(new MockMultipartFile("resImage", "test.jpg",
                                                MediaType.IMAGE_JPEG_VALUE, "test".getBytes()))
                                .param("name", "Test Restaurant")
                                .param("location", "Test City")
                                .param("contact", "1234567890")
                                .param("ownerId", "1"))
                                .andExpect(status().isCreated())
                                .andExpect(header().exists("Location"))
                                .andExpect(jsonPath("$.name").value("Test Restaurant"));
        }

        @Test
        void testUpdateRestaurant_Success() throws Exception {
                restaurant.setLocation("Updated City");
                when(restaurantService.updateRestaurant(eq(1L), any(), any(), any(), any(), any()))
                                .thenReturn(restaurant);

                mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PUT, "/api/restaurants/1")
                                .file(new MockMultipartFile("resImage", "update.jpg",
                                                MediaType.IMAGE_JPEG_VALUE, "update".getBytes()))
                                .param("name", "Updated Restaurant")
                                .param("location", "Updated City")
                                .param("contact", "0987654321")
                                .param("ownerId", "1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.location").value("Updated City"));
        }

        @Test
        void testDeleteRestaurant_Success() throws Exception {
                doNothing().when(restaurantService).deleteRestaurant(1L);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurants/1"))
                                .andExpect(status().isNoContent());
        }

        @Test
        void testDeleteRestaurant_NotFound() throws Exception {
                doThrow(new RestaurantNotFoundException("Restaurant not found with ID: 2"))
                                .when(restaurantService).deleteRestaurant(2L);

                mockMvc.perform(MockMvcRequestBuilders.delete("/api/restaurants/2"))
                                .andExpect(status().isNotFound())
                                .andDo(result -> System.out.println(
                                                "DELETE Response: " + result.getResponse().getContentAsString()))
                                .andExpect(jsonPath("$.message").value("Restaurant not found with ID: 2"));
        }

        @Disabled
        @Test
        void testGetImage_Success() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/image/test.jpg"))
                                .andExpect(status().isOk());
        }

        @Test
        void testGetImage_NotFound() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/api/restaurants/image/nonexistent.jpg"))
                                .andExpect(status().isNotFound());
        }
}