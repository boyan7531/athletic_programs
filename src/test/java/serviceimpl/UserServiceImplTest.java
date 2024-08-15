package serviceimpl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

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

    private User user;
    private UserLoginBindingModel userLoginBindingModel;
    private UserRegisterBindingModel userRegisterBindingModel;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("encodedpassword");

        userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setUsername("testuser");
        userLoginBindingModel.setPassword("password");

        userRegisterBindingModel = new UserRegisterBindingModel();
        userRegisterBindingModel.setUsername("newuser");
        userRegisterBindingModel.setPassword("newpassword");
        userRegisterBindingModel.setEmail("newuser@example.com");
        userRegisterBindingModel.setFullName("New User");
    }

    @Test
    public void testIsUniqueUsername() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        boolean isUnique = userService.isUniqueUsername("testuser");

        assertFalse(isUnique);
    }

    @Test
    public void testIsUniqueEmail() {
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        boolean isUnique = userService.isUniqueEmail("testuser@example.com");

        assertFalse(isUnique);
    }

    @Test
    public void testLoginSuccess() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);

        boolean loginResult = userService.login(userLoginBindingModel);

        assertTrue(loginResult);
        verify(userSession).login(user.getId(), user.getUsername());
        verify(userSession).setLogged(true);
    }

    @Test
    public void testLoginFailure() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

        boolean loginResult = userService.login(userLoginBindingModel);

        assertFalse(loginResult);
        verify(userSession, never()).login(anyLong(), anyString());
    }

    @Test
    public void testGetUserById() {
        when(userRepository.getById(1L)).thenReturn(user);

        User foundUser = userService.getUserById(1L);

        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testGetUserWithProgram() {
        when(userRepository.findUserWithProgramById(1L)).thenReturn(user);

        User foundUser = userService.getUserWithProgram(1L);

        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testRegister() {
        when(passwordEncoder.encode("newpassword")).thenReturn("encodednewpassword");

        userService.register(userRegisterBindingModel);

        verify(userRepository).saveAndFlush(any(User.class));
    }

    @Test
    public void testSave() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertEquals("testuser", savedUser.getUsername());
        verify(userRepository).save(user);
    }
}
