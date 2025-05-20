package com.capgemini.food_app;


import com.capgemini.food_app.dto.TopRestaurantDTO;
import com.capgemini.food_app.exception.RestaurantNotFoundException;
import com.capgemini.food_app.model.Restaurant;
import com.capgemini.food_app.repository.RestaurantRepository;
import com.capgemini.food_app.rest.RestaurantController;
import com.capgemini.food_app.service.RestaurantService;
import com.capgemini.food_app.service.TopRestaurantDTOService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private TopRestaurantDTOService topRestaurantDTOService;

    @InjectMocks
    private RestaurantController restaurantController;

    private Restaurant sampleRestaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleRestaurant = new Restaurant();
        sampleRestaurant.setId(1L);
        sampleRestaurant.setName("Test Restaurant");
        sampleRestaurant.setLocation("Test City");
        sampleRestaurant.setContact("1234567890");
        sampleRestaurant.setOwnerId(1L);
    }

    @Test
    @DisplayName("Get All Restaurants - Should return OK and restaurant list")
    void testGetAllRestaurants() {
        when(restaurantService.getAllRestaurants()).thenReturn(Collections.singletonList(sampleRestaurant));

        ResponseEntity<List<Restaurant>> response = restaurantController.getAllRestaurants();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Restaurant", response.getBody().get(0).getName());
        verify(restaurantService).getAllRestaurants();
    }

    @Test
    @DisplayName("Get Restaurant By ID - Should return OK and restaurant")
    void testGetRestaurantById() {
        when(restaurantService.getRestaurantById(1L)).thenReturn(sampleRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleRestaurant, response.getBody());
        verify(restaurantService).getRestaurantById(1L);
    }

    @Test
    @DisplayName("Get Restaurant By ID - Not Found should throw exception")
    void testGetRestaurantById_NotFound() {
        when(restaurantService.getRestaurantById(2L)).thenThrow(new RestaurantNotFoundException("Not found"));

        assertThrows(RestaurantNotFoundException.class, () -> restaurantController.getRestaurantById(2L));
        verify(restaurantService).getRestaurantById(2L);
    }

    @Test
    @DisplayName("Get Restaurants By Owner - Should return OK and restaurant list")
    void testGetRestaurantsByOwner() {
        when(restaurantService.getRestaurantByOwner(1L)).thenReturn(sampleRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantsByOwner(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleRestaurant, response.getBody());
        verify(restaurantService).getRestaurantByOwner(1L);
    }

    @Test
    @DisplayName("Get Restaurant Count - Should return OK and count")
    void testGetRestaurantCount() {
        when(restaurantService.getTotalRestaurantsCount()).thenReturn(5L);

        ResponseEntity<Long> response = restaurantController.getRestaurantCount();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5L, response.getBody());
        verify(restaurantService).getTotalRestaurantsCount();
    }

    @Test
    @DisplayName("Create Restaurant - Should return CREATED and restaurant")
    void testCreateRestaurant() throws IOException {
        MockMultipartFile file = new MockMultipartFile("resImage", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        when(restaurantService.createRestaurant(anyString(), anyString(), anyString(), anyLong(), any(MultipartFile.class)))
                .thenReturn(sampleRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.createRestaurant(
                "Test Restaurant", "Test City", "1234567890", 1L, file);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sampleRestaurant, response.getBody());
        verify(restaurantService).createRestaurant("Test Restaurant", "Test City", "1234567890", 1L, file);
    }

    @Test
    @DisplayName("Update Restaurant - Should return OK and updated restaurant")
    void testUpdateRestaurant() throws IOException {
        MockMultipartFile file = new MockMultipartFile("resImage", "update.jpg", MediaType.IMAGE_JPEG_VALUE, "update".getBytes());
        when(restaurantService.updateRestaurant(eq(1L), anyString(), anyString(), anyString(), anyLong(), any(MultipartFile.class)))
                .thenReturn(sampleRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.updateRestaurant(
                1L, "Updated Restaurant", "Updated City", "0987654321", 1L, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleRestaurant, response.getBody());
        verify(restaurantService).updateRestaurant(1L, "Updated Restaurant", "Updated City", "0987654321", 1L, file);
    }

    @Test
    @DisplayName("Patch Restaurant - Should return OK and patched restaurant")
    void testPatchRestaurant() throws IOException {
        MockMultipartFile file = new MockMultipartFile("resImage", "patch.jpg", MediaType.IMAGE_JPEG_VALUE, "patch".getBytes());
        when(restaurantService.patchRestaurant(eq(1L), anyString(), anyString(), anyString(), any(), any(MultipartFile.class)))
                .thenReturn(sampleRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.patchRestaurant(
                1L, "Patched", "Patched City", "0000000000", 1L, file);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleRestaurant, response.getBody());
        verify(restaurantService).patchRestaurant(1L, "Patched", "Patched City", "0000000000", 1L, file);
    }

    @Test
    @DisplayName("Delete Restaurant - Should return NO_CONTENT")
    void testDeleteRestaurant() {
        doNothing().when(restaurantService).deleteRestaurant(1L);

        ResponseEntity<Void> response = restaurantController.deleteRestaurant(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(restaurantService).deleteRestaurant(1L);
    }

    @Test
    @DisplayName("Get Image - Not Found")
    void testGetImage_NotFound() throws IOException {
        ResponseEntity<Resource> response = restaurantController.getImage("nonexistent.jpg");
        assertEquals(404, response.getStatusCodeValue());
    }
  

    @Test
    @DisplayName("Get Top Rated Restaurants - Should return list")
    void testGetTopRatedRestaurants() {
        TopRestaurantDTO dto = new TopRestaurantDTO();
        when(topRestaurantDTOService.findTopRestaurantsByAverageRating()).thenReturn(Collections.singletonList(dto));

        List<TopRestaurantDTO> result = restaurantController.getTopRatedRestaurants();

        assertEquals(1, result.size());
        verify(topRestaurantDTOService).findTopRestaurantsByAverageRating();
    }


}