package com.capgemini.food_app;

import com.capgemini.food_app.controller.FoodItemController;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

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

        List<FoodItem> result = foodItemController.getAllFoodItems();

        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
        verify(foodItemService, times(1)).getAllFoodItems();
    }

    @Test
    void testGetFoodItemById() {
        when(foodItemService.getFoodItemById(1L)).thenReturn(foodItem);

        FoodItem result = foodItemController.getFoodItemById(1L);

        assertEquals("Pizza", result.getName());
        verify(foodItemService).getFoodItemById(1L);
    }

    @Test
    void testCreateFoodItem() {
        when(foodItemService.createFoodItem(foodItem)).thenReturn(foodItem);

        FoodItem result = foodItemController.createFoodItem(foodItem);

        assertEquals("Pizza", result.getName());
        verify(foodItemService).createFoodItem(foodItem);
    }

    @Test
    void testUpdateFoodItem() {
        when(foodItemService.updateFoodItem(1L, foodItem)).thenReturn(foodItem);

        FoodItem result = foodItemController.updateFoodItem(1L, foodItem);

        assertEquals("Pizza", result.getName());
        verify(foodItemService).updateFoodItem(1L, foodItem);
    }

    @Test
    void testPatchFoodItem() {
        when(foodItemService.patchFoodItem(1L, foodItem)).thenReturn(foodItem);

        FoodItem result = foodItemController.patchFoodItem(1L, foodItem);

        assertEquals("Pizza", result.getName());
        verify(foodItemService).patchFoodItem(1L, foodItem);
    }

    @Test
    void testDeleteFoodItem() {
        doNothing().when(foodItemService).deleteFoodItem(1L);

        foodItemController.deleteFoodItem(1L);

        verify(foodItemService).deleteFoodItem(1L);
    }
}

