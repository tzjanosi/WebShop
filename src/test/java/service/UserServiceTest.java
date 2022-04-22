package service;

import entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserServiceRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("Register a user, happy path.")
    void registerUserTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com")).thenReturn(Optional.empty());
        when(userRepository.saveUser(any(User.class))).thenReturn(true);
        assertTrue(userService.registerUser("testuser@testdomain.com", "testPassword"));
        verify(userRepository).saveUser(argThat(user -> user.getEmail().equals("testuser@testdomain.com")));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    @DisplayName("Register a user's email already in the database")
    void registerUserAlreadyExistsTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(Optional.of(new User("testuser@testdomain.com", "testPassword")));
        assertFalse(userService.registerUser("testuser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    @DisplayName("Test trimming input strings")
    void registerUserTestWithLeadingAndTrailingWhiteSpacesTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com")).thenReturn(Optional.empty());
        when(userRepository.saveUser(any(User.class))).thenReturn(true);
        assertTrue(userService.registerUser("  testuser@testdomain.com  ", "  testPassword  "));
        verify(userRepository).saveUser(argThat(user -> user.getEmail().equals("testuser@testdomain.com")));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    @DisplayName("Test trimming input strings when user's email already in the database.")
    void registerUserAlreadyExistsWithLeadingAndTrailingWhiteSpacesTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(Optional.of(new User("testuser@testdomain.com", "testPassword")));
        assertFalse(userService.registerUser("  testuser@testdomain.com  ", "  testPassword  "));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    @DisplayName("Login user, happy path")
    void loginUserHappyPathTest() {
        Optional<User> user = Optional.of(new User("testuser@testdomain.com", "testPassword"));
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(user);
        assertSame(user, userService.loginUser("testuser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    @DisplayName("Test login with wrong password")
    void loginUserWrongPasswordTest() {
        Optional<User> user = Optional.of(new User("testuser@testdomain.com", "testPassword"));
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(user);
        assertEquals(Optional.empty(), userService.loginUser("testuser@testdomain.com", "anotherPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    @DisplayName("Test login with email not in the database")
    void loginUserNotExistsTest() {
        when(userRepository.findUserByEmail("noSuchUser@testdomain.com"))
                .thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.loginUser("noSuchUser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("noSuchUser@testdomain.com")));
    }
}