//package com.capgemini.food_app;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.capgemini.food_app.model.FoodItem;
//import com.capgemini.food_app.model.Restaurant;
//import com.capgemini.food_app.repository.FoodItemRepository;
//import com.capgemini.food_app.service.FoodItemServiceImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//public class FoodItemServiceImplTest {
//
//    @Mock
//    private FoodItemRepository foodItemRepo;
//
//    @InjectMocks
//    private FoodItemServiceImpl foodItemService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllFoodItems() {
//        List<FoodItem> items = Arrays.asList(
//            new FoodItem(1L, "Pizza", "Fast Food", 200, "img.jpg"),
//            new FoodItem(2L, "Burger", "Fast Food", 150, "img2.jpg")
//        );
//
//        when(foodItemRepo.findAll()).thenReturn(items);
//
//        List<FoodItem> result = foodItemService.getAllFoodItems();
//
//        assertEquals(2, result.size());
//        verify(foodItemRepo, times(1)).findAll();
//    }
//
//    @Test
//    void testGetFoodItemById() {
//        FoodItem item = new FoodItem(1L, "Pizza", "Fast Food", 200, "img.jpg");
//        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(item));
//
//        FoodItem result = foodItemService.getFoodItemById(1L);
//
//        assertEquals("Pizza", result.getName());
//        verify(foodItemRepo, times(1)).findById(1L);
//    }
//
//    @Test
//    void testCreateFoodItem() {
//        FoodItem item = new FoodItem(null, "Pasta", "Italian", 180, "img3.jpg");
//        FoodItem saved = new FoodItem(1L, "Pasta", "Italian", 180, "img3.jpg");
//
//        when(foodItemRepo.save(item)).thenReturn(saved);
//
//        FoodItem result = foodItemService.createFoodItem(item);
//
//        assertNotNull(result.getId());
//        assertEquals("Pasta", result.getName());
//        verify(foodItemRepo).save(item);
//    }
//
//    @Test
//    void testUpdateFoodItem() {
//        FoodItem existing = new FoodItem(1L, "Pizza", "Fast Food", 200, "img.jpg");
//        FoodItem updated = new FoodItem(1L, "Cheese Pizza", "Italian", 250, "img2.jpg");
//
//        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(existing));
//        when(foodItemRepo.save(any(FoodItem.class))).thenReturn(updated);
//
//        FoodItem result = foodItemService.updateFoodItem(1L, updated);
//
//        assertEquals("Cheese Pizza", result.getName());
//        assertEquals("Italian", result.getCategory());
//        assertEquals(250, result.getPrice());
//        verify(foodItemRepo).save(existing);
//    }
//
//    @Test
//    void testDeleteFoodItem() {
//        doNothing().when(foodItemRepo).deleteById(1L);
//
//        foodItemService.deleteFoodItem(1L);
//
//        verify(foodItemRepo, times(1)).deleteById(1L);
//    }
//
//    @Test
//    void testGetFoodItemsForRestaurant() {
//        Restaurant restaurant = new Restaurant();
//        restaurant.setId(1L);
//
//        List<FoodItem> items = Arrays.asList(
//            new FoodItem(1L, "Pizza", "Fast Food", 200, "img.jpg", restaurant)
//        );
//
//        when(foodItemRepo.findAllByRestaurantId(1L)).thenReturn(items);
//
//        List<FoodItem> result = foodItemService.getFoodItemsForRestaurant(1L);
//
//        assertEquals(1, result.size());
//        verify(foodItemRepo).findAllByRestaurantId(1L);
//    }
//
//    @Test
//    void testPatchFoodItem() {
//        FoodItem existing = new FoodItem(1L, "Pizza", "Fast Food", 200, "img.jpg");
//        FoodItem patchData = new FoodItem();
//        patchData.setName("Veg Pizza");
//        patchData.setPrice(220);
//
//        when(foodItemRepo.findById(1L)).thenReturn(Optional.of(existing));
//        when(foodItemRepo.save(any(FoodItem.class))).thenReturn(existing);
//
//        FoodItem result = foodItemService.patchFoodItem(1L, patchData);
//
//        assertEquals("Veg Pizza", result.getName());
//        assertEquals(220, result.getPrice());
//        verify(foodItemRepo).save(existing);
//    }
//
//    @Test
//    void testGetFoodItemById_NotFound() {
//        when(foodItemRepo.findById(99L)).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            foodItemService.getFoodItemById(99L);
//        });
//
//        assertEquals("Food item not found by id", exception.getMessage());
//    }
//}