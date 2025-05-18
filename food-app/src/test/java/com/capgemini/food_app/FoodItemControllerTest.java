package com.capgemini.food_app;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.rest.FoodItemController;
import com.capgemini.food_app.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodItemControllerTest {

    @Mock
    private FoodItemService foodItemService;

    @InjectMocks
    private FoodItemController foodItemController;

    private FoodItem foodItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        foodItem = new FoodItem(1L, "Pizza", "Fast Food", 300, "pizza.jpg", "Italian");
    }

    @Test
    void testGetAllFoodItems() {
        List<FoodItem> foodItems = Arrays.asList(foodItem);
        when(foodItemService.getAllFoodItems()).thenReturn(foodItems);

        ResponseEntity<List<FoodItem>> response = foodItemController.getAllFoodItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Pizza", response.getBody().get(0).getName());
        verify(foodItemService).getAllFoodItems();
    }

    @Test
    void testGetFoodItemById() {
        when(foodItemService.getFoodItemById(1L)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pizza", response.getBody().getName());
        verify(foodItemService).getFoodItemById(1L);
    }

    @Test
    void testGetFoodItemById_NotFound() {
        when(foodItemService.getFoodItemById(99L)).thenReturn(null);

        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(foodItemService).getFoodItemById(99L);
    }

    @Test
    void testCreateFoodItem() {
        when(foodItemService.createFoodItem(foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.createFoodItem(foodItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(foodItem, response.getBody());
        verify(foodItemService).createFoodItem(foodItem);
    }

    @Test
    void testUpdateFoodItem() {
        when(foodItemService.updateFoodItem(1L, foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.updateFoodItem(1L, foodItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItem, response.getBody());
        verify(foodItemService).updateFoodItem(1L, foodItem);
    }

    @Test
    void testPatchFoodItem() {
        when(foodItemService.patchFoodItem(1L, foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.patchFoodItem(1L, foodItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(foodItem, response.getBody());
        verify(foodItemService).patchFoodItem(1L, foodItem);
    }

    @Test
    void testDeleteFoodItem() {
        doNothing().when(foodItemService).deleteFoodItem(1L);

        ResponseEntity<Void> response = foodItemController.deleteFoodItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(foodItemService).deleteFoodItem(1L);
    }
}
