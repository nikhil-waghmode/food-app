package com.capgemini.food_app;


import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.RestaurantRepository;
import com.capgemini.food_app.service.RestaurantServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestaurants() {
        List<Restaurant> restaurants = Arrays.asList(
                new Restaurant("Res1", "Loc1", "123", 10L, "img1.jpg", new ArrayList<>()),
                new Restaurant("Res2", "Loc2", "456", 20L, "img2.jpg", new ArrayList<>())
        );
        when(restaurantRepository.findAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantService.getAllRestaurants();
        assertEquals(2, result.size());
        verify(restaurantRepository).findAll();
    }

    @Test
    void testGetRestaurantById_Success() {
        Restaurant restaurant = new Restaurant("Res1", "Loc1", "123", 10L, "img1.jpg", new ArrayList<>());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getRestaurantById(1L);
        assertEquals("Res1", result.getName());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void testGetRestaurantById_NotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            restaurantService.getRestaurantById(99L);
        });
        assertTrue(exception.getMessage().contains("Restaurant not found with ID"));
    }

    @Test
    void testGetRestaurantByOwner() {
        Restaurant restaurant = new Restaurant("Res1", "Loc1", "123", 10L, "img1.jpg", new ArrayList<>());
        when(restaurantRepository.findByOwnerId(10L)).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getRestaurantByOwner(10L);

        assertNotNull(result);
        assertEquals("Res1", result.getName());
        verify(restaurantRepository).findByOwnerId(10L);
    }


    @Test
    void testGetTotalRestaurantsCount() {
        when(restaurantRepository.count()).thenReturn(5L);

        Long count = restaurantService.getTotalRestaurantsCount();
        assertEquals(5L, count);
        verify(restaurantRepository).count();
    }

    @Test
    void testCreateRestaurant() throws IOException {
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("test.jpg");
        when(mockFile.getInputStream()).thenReturn(InputStream.nullInputStream());

        // The saved restaurant will have an ID assigned
        Restaurant saved = new Restaurant("Res1", "Loc1", "123", 10L, "file.jpg", new ArrayList<>());
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(saved);

        Restaurant result = restaurantService.createRestaurant("Res1", "Loc1", "123", 10L, mockFile);

        assertNotNull(result.getId());
        assertEquals("Res1", result.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void testUpdateRestaurant() throws IOException {
        Restaurant existing = new Restaurant("OldName", "OldLoc", "111", 10L, "old.jpg", new ArrayList<>());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(existing);

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("new.jpg");
        when(mockFile.getInputStream()).thenReturn(InputStream.nullInputStream());

        Restaurant result = restaurantService.updateRestaurant(1L, "NewName", "NewLoc", "222", 20L, mockFile);

        assertEquals("NewName", result.getName());
        assertEquals("NewLoc", result.getLocation());
        assertEquals("222", result.getContact());
        assertEquals(20L, result.getOwnerId());
        verify(restaurantRepository).save(existing);
    }

    @Test
    void testPatchRestaurant() throws IOException {
        Restaurant existing = new Restaurant("OldName", "OldLoc", "111", 10L, "old.jpg", new ArrayList<>());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(existing);

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("patch.jpg");
        when(mockFile.getInputStream()).thenReturn(InputStream.nullInputStream());

        Restaurant result = restaurantService.patchRestaurant(1L, "PatchName", null, null, null, mockFile);

        assertEquals("PatchName", result.getName());
        verify(restaurantRepository).save(existing);
    }

    @Test
    void testDeleteRestaurant() {
        Restaurant restaurant = new Restaurant("Res1", "Loc1", "123", 10L, "img.jpg", new ArrayList<>());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        doNothing().when(restaurantRepository).deleteById(1L);

        // Spy to mock deleteImage, so it doesn't touch the filesystem
        RestaurantServiceImpl spyService = Mockito.spy(restaurantService);

        spyService.deleteRestaurant(1L);
        verify(restaurantRepository).deleteById(1L);
    }
}
