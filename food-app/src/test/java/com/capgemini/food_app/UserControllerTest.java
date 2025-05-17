package com.capgemini.food_app;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.rest.UserController;
import com.capgemini.food_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Milind");
        user.setEmail("milind@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setUserType("Customer");
        user.setLocation("Pune");
        user.setUserImg("image.jpg");
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/users"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()").value(1))
               .andExpect(jsonPath("$[0].name").value("Milind"));
    }

    @Test
    void testCreateUser() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "userImg", "profile.jpg", "image/jpeg", "dummy".getBytes()
        );
        when(userService.createUser(
                eq("Milind"), eq("milind@example.com"), eq("password123"),
                eq("1234567890"), eq("Pune"), eq("Customer"),
                any(MockMultipartFile.class))
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users")
                .file(file)
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("phone", user.getPhone())
                .param("location", user.getLocation())
                .param("userType", user.getUserType())
        )
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/users/1"))
        .andExpect(jsonPath("$.name").value("Milind"));
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email").value("milind@example.com"));
    }

    @Test
    void testDeleteUser_Success() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/users/1"))
               .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUser_NotFound() throws Exception {
        when(userService.deleteUser(2L)).thenReturn(false);

        mockMvc.perform(delete("/api/users/2"))
               .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateUser_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "userImg", "update.jpg", "image/jpeg", "update".getBytes()
        );
        when(userService.updateUser(
                eq(1L), eq("Milind"), eq("milind@example.com"), eq("password123"),
                eq("1234567890"), eq("Pune"), eq("Customer"),
                any(MockMultipartFile.class))
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users/1")
                .file(file)
                .with(request -> { request.setMethod("PUT"); return request; })
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("phone", user.getPhone())
                .param("location", user.getLocation())
                .param("userType", user.getUserType())
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Milind"));
    }

    @Test
    void testUpdateUser_NotFound() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "userImg", "", "application/octet-stream", new byte[0]
        );
        when(userService.updateUser(
                eq(2L), anyString(), anyString(), anyString(),
                anyString(), anyString(), anyString(),
                any(MockMultipartFile.class))
        ).thenReturn(null);

        mockMvc.perform(multipart("/api/users/2")
                .file(file)
                .with(request -> { request.setMethod("PUT"); return request; })
                .param("name", user.getName())
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .param("phone", user.getPhone())
                .param("location", user.getLocation())
                .param("userType", user.getUserType())
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void testPatchUser_Success() throws Exception {
        user.setLocation("Mumbai");
        MockMultipartFile file = new MockMultipartFile(
            "userImg", "", "application/octet-stream", new byte[0]
        );
        when(userService.patchUser(
                eq(1L), isNull(), isNull(), isNull(),
                isNull(), eq("Mumbai"), isNull(),
                any(MockMultipartFile.class))
        ).thenReturn(user);

        mockMvc.perform(multipart("/api/users/1")
                .file(file)
                .with(request -> { request.setMethod("PATCH"); return request; })
                .param("location", "Mumbai")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.location").value("Mumbai"));
    }

    
}
