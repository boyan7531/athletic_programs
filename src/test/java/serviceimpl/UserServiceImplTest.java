package serviceimpl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSession userSession;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isUniqueUsername_ShouldReturnTrue_WhenUsernameIsUnique() {
        // Arrange
        when(userRepository.findByUsername("uniqueUsername")).thenReturn(Optional.empty());

        // Act
        boolean result = userService.isUniqueUsername("uniqueUsername");

        // Assert
        assertTrue(result);
    }

    @Test
    void isUniqueUsername_ShouldReturnFalse_WhenUsernameIsNotUnique() {
        // Arrange
        User existingUser = new User();
        when(userRepository.findByUsername("existingUsername")).thenReturn(Optional.of(existingUser));

        // Act
        boolean result = userService.isUniqueUsername("existingUsername");

        // Assert
        assertFalse(result);
    }

    @Test
    void login_ShouldReturnTrue_WhenCredentialsAreValid() {
        // Arrange
        User user = new User();
        user.setUsername("validUser");
        user.setPassword("encodedPassword");

        UserLoginBindingModel loginModel = new UserLoginBindingModel();
        loginModel.setUsername("validUser");
        loginModel.setPassword("password");

        when(userRepository.findByUsername("validUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Act
        boolean result = userService.login(loginModel);

        // Assert
        assertTrue(result);
        verify(userSession).login(user.getId(), user.getUsername());
        verify(userSession).setCurrentUser(user);
        verify(userSession).setLogged(true);
    }

    @Test
    void login_ShouldReturnFalse_WhenUserNotFound() {
        // Arrange
        UserLoginBindingModel loginModel = new UserLoginBindingModel();
        loginModel.setUsername("invalidUser");

        when(userRepository.findByUsername("invalidUser")).thenReturn(Optional.empty());

        // Act
        boolean result = userService.login(loginModel);

        // Assert
        assertFalse(result);
        verify(userSession, never()).login(any(), any());
    }

    @Test
    void register_ShouldSaveNewUser() {
        // Arrange
        UserRegisterBindingModel registerModel = new UserRegisterBindingModel();
        registerModel.setUsername("newUser");
        registerModel.setPassword("password");
        registerModel.setEmail("user@example.com");
        registerModel.setFullName("New User");

        User user = new User();
        user.setUsername("newUser");
        user.setPassword("encodedPassword");
        user.setEmail("user@example.com");
        user.setFullName("New User");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.register(registerModel);

        // Assert
        verify(userRepository).saveAndFlush(any(User.class));
    }

    @Test
    void changeUsername_ShouldReturnFalse_WhenNewUsernameIsSame() {
        // Arrange
        User user = new User();
        user.setUsername("sameUsername");

        // Act
        boolean result = userService.changeUsername(user, "sameUsername");

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    void changeUsername_ShouldReturnFalse_WhenUsernameAlreadyExists() {
        // Arrange
        User user = new User();
        user.setUsername("currentUsername");

        User existingUser = new User();
        existingUser.setUsername("newUsername");

        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.of(existingUser));

        // Act
        boolean result = userService.changeUsername(user, "newUsername");

        // Assert
        assertFalse(result);
        verify(userRepository, never()).save(user);
    }

    @Test
    void changeUsername_ShouldReturnTrue_WhenUsernameIsChanged() {
        // Arrange
        User user = new User();
        user.setUsername("currentUsername");

        when(userRepository.findByUsername("newUsername")).thenReturn(Optional.empty());

        // Act
        boolean result = userService.changeUsername(user, "newUsername");

        // Assert
        assertTrue(result);
        verify(userRepository).save(user);
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    void validatePassword_ShouldReturnTrue_WhenPasswordMatches() {
        // Arrange
        User user = new User();
        user.setPassword("encodedPassword");

        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        // Act
        boolean result = userService.validatePassword(user, "password");

        // Assert
        assertTrue(result);
    }

    @Test
    void validatePassword_ShouldReturnFalse_WhenPasswordDoesNotMatch() {
        // Arrange
        User user = new User();
        user.setPassword("encodedPassword");

        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // Act
        boolean result = userService.validatePassword(user, "wrongPassword");

        // Assert
        assertFalse(result);
    }
}
