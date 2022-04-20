package service;

import entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserServiceRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void registerUserTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com")).thenReturn(Optional.empty());
        when(userRepository.saveUser(any(User.class))).thenReturn(true);
        assertTrue(userService.registerUser("testuser@testdomain.com", "testPassword"));
    }

//    @Test
//    void loginUserTest() {
//    }
}