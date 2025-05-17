package com.capgemini.food_app;

import com.capgemini.food_app.exception.EmailAlreadyExistsException;
import com.capgemini.food_app.exception.UserNotFoundException;
import com.capgemini.food_app.model.User;
import com.capgemini.food_app.repository.UserRepository;
import com.capgemini.food_app.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Pooja");
        user.setEmail("pooja@example.com");
        user.setPassword("password123");
        user.setPhone("1234567890");
        user.setUserType("Customer");
        user.setLocation("Pune");
        user.setUserImg(null);
    }

    @Test
    void testCreateUser() throws IOException {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // call service with parameters, no image
        User created = userService.createUser(
            user.getName(), user.getEmail(), user.getPassword(),
            user.getPhone(), user.getLocation(), user.getUserType(),
            null
        );

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Pooja");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserExists() throws IOException {
        // stub existing user
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(
            1L,
            "Milind", "milind@example.com", "milind", "9998887777",
            "Satara", "Admin", null
        );

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Milind");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserNotExists() throws IOException {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(
            2L,
            "Name", "email@example.com", "pass", "phone",
            "loc", "type", null
        )).isInstanceOf(UserNotFoundException.class);

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_UserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean deleted = userService.deleteUser(1L);

        assertThat(deleted).isTrue();
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotExists() {
        when(userRepository.existsById(2L)).thenReturn(false);

        boolean deleted = userService.deleteUser(2L);

        assertThat(deleted).isFalse();
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertThat(result.size()).isEqualTo(2);
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getUserById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Pooja");
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        User result = userService.getUserById(2L);

        assertThat(result).isNull();
    }

    @Test
    void testPatchUser_UserExists() throws IOException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.patchUser(
            1L,
            null, "abc@example.com", null,
            null, "Mumbai", null, null
        );

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("abc@example.com");
        assertThat(result.getLocation()).isEqualTo("Mumbai");
        verify(userRepository).save(any(User.class));
    }

}
