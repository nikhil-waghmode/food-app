package com.capgemini.food_app;


import com.capgemini.food_app.model.User;
import com.capgemini.food_app.rest.UserController;
import com.capgemini.food_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User("John Doe", "john@example.com", "password", "1234567890", "CUSTOMER", "NY", "img.jpg");
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testCreateUser() throws Exception {
        MockMultipartFile file = new MockMultipartFile("userImg", "img.jpg", "image/jpeg", "dummy".getBytes());

        when(userService.createUser(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any())
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users")
                        .file(file)
                        .param("name", "John Doe")
                        .param("email", "john@example.com")
                        .param("password", "password")
                        .param("phone", "1234567890")
                        .param("location", "NY")
                        .param("userType", "CUSTOMER")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateUser() throws Exception {
        MockMultipartFile file = new MockMultipartFile("userImg", "img.jpg", "image/jpeg", "dummy".getBytes());

        when(userService.updateUser(
                eq(1L), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any())
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users/1")
                        .file(file)
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .param("name", "John Doe")
                        .param("email", "john@example.com")
                        .param("password", "password")
                        .param("phone", "1234567890")
                        .param("location", "NY")
                        .param("userType", "CUSTOMER")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testPatchUser() throws Exception {
        MockMultipartFile file = new MockMultipartFile("userImg", "img.jpg", "image/jpeg", "dummy".getBytes());

        when(userService.patchUser(
                eq(1L), any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users/1")
                        .file(file)
                        .with(request -> { request.setMethod("PATCH"); return request; })
                        .param("name", "John Doe")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNotFound());
    }
}