package com.capgemini.food_app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.capgemini.food_app.model.User;
import com.capgemini.food_app.repository.UserRepository;
import com.capgemini.food_app.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Pooja");
        user.setEmail("pooja@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setUserType("Customer");
        user.setLocation("Pune");
        user.setUserImg("image.jpg");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User created = userService.createUser(user);

        assertNotNull(created);
        assertEquals("Pooja", created.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser_UserExists() {
        User updated = new User();
        updated.setName("Milind");
        updated.setEmail("milind@example.com");
        updated.setPassword("milind");
        updated.setPhone("9998887777");
        updated.setUserType("Admin");
        updated.setLocation("Satara");
        updated.setUserImg("img2.jpg");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updated);

        User result = userService.updateUser(1L, updated);

        assertNotNull(result);
        assertEquals("Milind", result.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotExists() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.updateUser(2L, user);

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        boolean deleted = userService.deleteUser(1L);

        assertTrue(deleted);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotExists() {
        when(userRepository.existsById(2L)).thenReturn(false);

        boolean deleted = userService.deleteUser(2L);

        assertFalse(deleted);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals("Pooja", found.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.getUserById(2L);

        assertNull(result);
    }

    @Test
    void testPatchUser_UserExists() {
        User patch = new User();
        patch.setEmail("abc@example.com");
        patch.setLocation("Mumbai");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.patchUser(1L, patch);

        assertNotNull(result);
        assertEquals("abc@example.com", result.getEmail());
        assertEquals("Mumbai", result.getLocation());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testPatchUser_UserNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.patchUser(2L, new User());

        assertNull(result);
        verify(userRepository, never()).save(any(User.class));
    }
}
