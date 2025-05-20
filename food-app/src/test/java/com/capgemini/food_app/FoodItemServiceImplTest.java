package com.capgemini.food_app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.capgemini.food_app.exception.RestaurantNotFoundException;
import com.capgemini.food_app.model.FoodItem;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.FoodItemRepository;
import com.capgemini.food_app.service.FoodItemServiceImpl;
import com.capgemini.food_app.service.RestaurantService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FoodItemServiceImplTest {

    @Mock
    private FoodItemRepository foodItemRepo;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private FoodItemServiceImpl foodItemService;

    private Restaurant mockRestaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockRestaurant = new Restaurant();
        mockRestaurant.setId(1L);
        mockRestaurant.setName("Mock Restaurant");
    }

    @Test
    void testGetAllFoodItems() {
        List<FoodItem> items = Arrays.asList(
            new FoodItem("Pizza", "Fast Food", 200, "img.jpg", mockRestaurant),
            new FoodItem("Burger", "Fast Food", 150, "img2.jpg", mockRestaurant)
        );

        when(foodItemRepo.findAll()).thenReturn(items);

        List<FoodItem> result = foodItemService.getAllFoodItems();

        assertEquals(2, result.size());
        verify(foodItemRepo, times(1)).findAll();
    }

    @Test
    void testGetFoodItemById() {
        FoodItem item = new FoodItem("Pizza", "Fast Food", 200, "img.jpg", mockRestaurant);
        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(item));

        FoodItem result = foodItemService.getFoodItemById(1L);

        assertEquals("Pizza", result.getName());
        verify(foodItemRepo, times(1)).findById(1L);
    }

    @Test
    void testCreateFoodItem() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "pizza.jpg", "image/jpeg", "image".getBytes());

        when(restaurantService.getRestaurantById(1L)).thenReturn(mockRestaurant);
        when(foodItemRepo.save(any(FoodItem.class))).thenAnswer(i -> i.getArgument(0));

        FoodItem result = foodItemService.createFoodItem(
                "Pizza", "Fast Food", 200, "Italian", 1L, file
        );

        assertEquals("Pizza", result.getName());
        assertEquals("Fast Food", result.getCategory());
        assertEquals(mockRestaurant, result.getRestaurant());
        verify(foodItemRepo).save(any(FoodItem.class));
    }

    @Test
    void testUpdateFoodItem() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "updated.jpg", "image/jpeg", "image".getBytes());
        FoodItem existing = new FoodItem("Pizza", "Fast Food", 200, "img.jpg", mockRestaurant);
        existing.setId(1L);

        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(restaurantService.getRestaurantById(1L)).thenReturn(mockRestaurant);
        when(foodItemRepo.save(any(FoodItem.class))).thenAnswer(i -> i.getArgument(0));

        FoodItem result = foodItemService.updateFoodItem(
                1L, "Cheese Pizza", "Fast Food", 250, "Italian", 1L, file
        );

        assertEquals("Cheese Pizza", result.getName());
        assertEquals(250, result.getPrice());
        verify(foodItemRepo).save(existing);
    }

    @Test
    void testPatchFoodItem() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "patch.jpg", "image/jpeg", "image".getBytes());
        FoodItem existing = new FoodItem("Pizza", "Fast Food", 200, "img.jpg", mockRestaurant);
        existing.setId(1L);

        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(restaurantService.getRestaurantById(1L)).thenReturn(mockRestaurant);
        when(foodItemRepo.save(any(FoodItem.class))).thenAnswer(i -> i.getArgument(0));

        FoodItem result = foodItemService.patchFoodItem(
                1L, "Veg Pizza", null, 220, null, 1L, file
        );

        assertEquals("Veg Pizza", result.getName());
        assertEquals(220, result.getPrice());
        verify(foodItemRepo).save(existing);
    }

    @Test
    void testDeleteFoodItem() {
        when(foodItemRepo.existsById(1L)).thenReturn(true);
        doNothing().when(foodItemRepo).deleteById(1L);

        boolean result = foodItemService.deleteFoodItem(1L);

        assertTrue(result);
        verify(foodItemRepo).deleteById(1L);
    }

    @Test
    void testGetFoodItemsForRestaurant() {
        List<FoodItem> items = Arrays.asList(
            new FoodItem("Pizza", "Fast Food", 200, "img.jpg", mockRestaurant)
        );

        when(foodItemRepo.findAllByRestaurantId(1L)).thenReturn(items);

        List<FoodItem> result = foodItemService.getFoodItemsForRestaurant(1L);

        assertEquals(1, result.size());
        verify(foodItemRepo).findAllByRestaurantId(1L);
    }

    @Test
    void testGetFoodItemById_NotFound() {
        when(foodItemRepo.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RestaurantNotFoundException.class, () -> {
            foodItemService.getFoodItemById(99L);
        });

        assertEquals("Food item not found with id: 99", exception.getMessage());
    }
}
