package com.capgemini.food_app;

import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.rest.FoodItemController;
import com.capgemini.food_app.service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
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
        foodItem = new FoodItem();
        foodItem.setName("Pizza");
        foodItem.setCategory("Fast Food");
        foodItem.setPrice(300);
        foodItem.setItemImg("pizza.jpg");
        foodItem.setCuisine("Italian");
    }

    @Test
    void testGetAllFoodItems() {
        when(foodItemService.getAllFoodItems()).thenReturn(Arrays.asList(foodItem));

        ResponseEntity<List<FoodItem>> response = foodItemController.getAllFoodItems();

        assertEquals(1, response.getBody().size());
        assertEquals("Pizza", response.getBody().get(0).getName());
        verify(foodItemService).getAllFoodItems();
    }

    @Test
    void testGetFoodItemById() {
        when(foodItemService.getFoodItemById(1L)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.getFoodItemById(1L);

        assertEquals("Pizza", response.getBody().getName());
        verify(foodItemService).getFoodItemById(1L);
    }

    @Test
    void testCreateFoodItem() throws IOException {
        MockMultipartFile file = new MockMultipartFile("foodImage", "pizza.jpg", "image/jpeg", "fake image".getBytes());

        when(foodItemService.createFoodItem("Pizza", "Fast Food", 300, "Italian", 1L, file)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.createFoodItem(
                "Pizza", "Fast Food", 300, "Italian", 1L, file
        );

        assertEquals("Pizza", response.getBody().getName());
        verify(foodItemService).createFoodItem("Pizza", "Fast Food", 300, "Italian", 1L, file);
    }

    @Test
    void testUpdateFoodItem() throws IOException {
        MockMultipartFile file = new MockMultipartFile("foodImage", "pizza.jpg", "image/jpeg", "fake image".getBytes());

        when(foodItemService.updateFoodItem(1L, "Pizza", "Fast Food", 300, "Italian", 1L, file)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.updateFoodItem(
                1L, "Pizza", "Fast Food", 300, "Italian", 1L, file
        );

        assertEquals("Pizza", response.getBody().getName());
        verify(foodItemService).updateFoodItem(1L, "Pizza", "Fast Food", 300, "Italian", 1L, file);
    }

    @Test
    void testPatchFoodItem() throws IOException {
        MockMultipartFile file = new MockMultipartFile("foodImage", "pizza.jpg", "image/jpeg", "fake image".getBytes());

        when(foodItemService.patchFoodItem(1L, "Pizza", "Fast Food", 300, "Italian", 1L, file)).thenReturn(foodItem);

        ResponseEntity<FoodItem> response = foodItemController.patchFoodItem(
                1L, "Pizza", "Fast Food", 300, "Italian", 1L, file
        );

        assertEquals("Pizza", response.getBody().getName());
        verify(foodItemService).patchFoodItem(1L, "Pizza", "Fast Food", 300, "Italian", 1L, file);
    }

    @Test
    void testDeleteFoodItem() {
        when(foodItemService.deleteFoodItem(1L)).thenReturn(true);

        ResponseEntity<Void> response = foodItemController.deleteFoodItem(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(foodItemService).deleteFoodItem(1L);
    }
}
