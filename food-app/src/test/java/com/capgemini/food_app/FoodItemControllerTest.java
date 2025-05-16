package com.capgemini.food_app;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.rest.FoodItemController;
import com.capgemini.food_app.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
        List<FoodItem> result = response.getBody();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getName());
        verify(foodItemService, times(1)).getAllFoodItems();
    }

    @Test
    void testGetFoodItemById() {
        when(foodItemService.getFoodItemById(1L)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(1L);
        FoodItem result = response.getBody();

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(foodItemService).getFoodItemById(1L);
    }

    @Test
    void testCreateFoodItem() {
        // Mock MultipartFile as null or a mock if needed
        MultipartFile mockFile = mock(MultipartFile.class);

        when(foodItemService.createFoodItemWithImage(
                eq("Pizza"),
                eq("Fast Food"),
                eq(300),
                eq("Italian"),
                eq(1L),
                eq(mockFile)
        )).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.createFoodItem(
                "Pizza",
                "Fast Food",
                300,
                "Italian",
                1L,
                mockFile
        );

        FoodItem result = response.getBody();

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        assertEquals(201, response.getStatusCodeValue());
        verify(foodItemService).createFoodItemWithImage(
                anyString(), anyString(), anyInt(), anyString(), anyLong(), any(MultipartFile.class));
    }

    @Test
    void testUpdateFoodItem() {
        when(foodItemService.updateFoodItem(1L, foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.updateFoodItem(1L, foodItem);
        FoodItem result = response.getBody();

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(foodItemService).updateFoodItem(1L, foodItem);
    }

    @Test
    void testPatchFoodItem() {
        when(foodItemService.patchFoodItem(1L, foodItem)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.patchFoodItem(1L, foodItem);
        FoodItem result = response.getBody();

        assertNotNull(result);
        assertEquals("Pizza", result.getName());
        verify(foodItemService).patchFoodItem(1L, foodItem);
    }

    @Test
    void testDeleteFoodItem() {
        when(foodItemService.deleteFoodItem(1L)).thenReturn(true);

        ResponseEntity<Void> response = foodItemController.deleteFoodItem(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(foodItemService).deleteFoodItem(1L);
    }

    // Optional: Add tests for exception scenarios when service returns null or false
}
