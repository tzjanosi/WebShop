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
import static org.mockito.Mockito.*;

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
        verify(userRepository).saveUser(argThat(user -> user.getEmail().equals("testuser@testdomain.com")));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    void registerUserAlreadyExistsTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(Optional.of(new User("testuser@testdomain.com", "testPassword")));
        assertFalse(userService.registerUser("testuser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    void registerUserTestWithLeadingAndTrailingWhiteSpacesTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com")).thenReturn(Optional.empty());
        when(userRepository.saveUser(any(User.class))).thenReturn(true);
        assertTrue(userService.registerUser("  testuser@testdomain.com  ", "  testPassword  "));
        verify(userRepository).saveUser(argThat(user -> user.getEmail().equals("testuser@testdomain.com")));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    void registerUserAlreadyExistsWithLeadingAndTrailingWhiteSpacesTest() {
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(Optional.of(new User("testuser@testdomain.com", "testPassword")));
        assertFalse(userService.registerUser("  testuser@testdomain.com  ", "  testPassword  "));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
        verify(userRepository, never()).saveUser(any(User.class));
    }

    @Test
    void loginUserHappyPathTest() {
        Optional<User> user = Optional.of(new User("testuser@testdomain.com", "testPassword"));
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(user);
        assertSame(user, userService.loginUser("testuser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    void loginUserWrongPasswordTest() {
        Optional<User> user = Optional.of(new User("testuser@testdomain.com", "testPassword"));
        when(userRepository.findUserByEmail("testuser@testdomain.com"))
                .thenReturn(user);
        assertEquals(Optional.empty(), userService.loginUser("testuser@testdomain.com", "anotherPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("testuser@testdomain.com")));
    }

    @Test
    void loginUserNotExistsTest() {
        when(userRepository.findUserByEmail("noSuchUser@testdomain.com"))
                .thenReturn(Optional.empty());
        assertEquals(Optional.empty(), userService.loginUser("noSuchUser@testdomain.com", "testPassword"));
        verify(userRepository).findUserByEmail(argThat(email -> email.equals("noSuchUser@testdomain.com")));
    }
}