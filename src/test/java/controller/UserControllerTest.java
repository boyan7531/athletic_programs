package controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.controller.UserController;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserSession userSession;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewLogin_NotLoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(false);

        String viewName = userController.viewLogin();

        assertEquals("login", viewName);
    }

    @Test
    public void testViewLogin_LoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(true);

        String viewName = userController.viewLogin();

        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testDoLogin_WithErrors() {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        when(userSession.isLoggedIn()).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.doLogin(userLoginBindingModel, bindingResult, redirectAttributes);

        assertEquals("redirect:/users/login", viewName);
        verify(redirectAttributes).addFlashAttribute("loginError", userLoginBindingModel);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
    }

    @Test
    public void testDoLogin_Success() {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        when(userSession.isLoggedIn()).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.login(userLoginBindingModel)).thenReturn(true);

        String viewName = userController.doLogin(userLoginBindingModel, bindingResult, redirectAttributes);

        assertEquals("redirect:/", viewName);
        verify(userService).login(userLoginBindingModel);
        verify(userSession).setLogged(true);
    }

    @Test
    public void testDoLogin_Failure() {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        when(userSession.isLoggedIn()).thenReturn(false);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.login(userLoginBindingModel)).thenReturn(false);

        String viewName = userController.doLogin(userLoginBindingModel, bindingResult, redirectAttributes);

        assertEquals("redirect:/users/login", viewName);
        verify(redirectAttributes).addFlashAttribute("loginError", userLoginBindingModel);
    }

    @Test
    public void testRegister_WithErrors() {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.register(userRegisterBindingModel, bindingResult, redirectAttributes);

        assertEquals("redirect:/users/register", viewName);
        verify(redirectAttributes).addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
    }

    @Test
    public void testRegister_Success() {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.register(userRegisterBindingModel, bindingResult, redirectAttributes);

        assertEquals("redirect:/users/login", viewName);
        verify(userService).register(userRegisterBindingModel);
    }

    @Test
    public void testLogout_NotLoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(false);

        String viewName = userController.logout();

        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testLogout_LoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(true);

        String viewName = userController.logout();

        assertEquals("redirect:/users/logout", viewName);
        verify(userSession).setLogged(false);
    }

    @Test
    public void testShowProfile() {
        Long userId = 1L;
        User user = new User();
        user.setUsername("testUser");

        when(userSession.getId()).thenReturn(userId);
        when(userService.findById(userId)).thenReturn(Optional.of(user));

        String viewName = userController.showProfile(model);

        assertEquals("profile", viewName);
        verify(model).addAttribute("username", user.getUsername());
        verify(model).addAttribute("enrolledProgram", "No Program Assigned");
        verify(model).addAttribute("runs", user.getRuns());
        verify(model).addAttribute("favoriteMeals", user.getFavoriteMeals());
    }

    @Test
    public void testShowChangeUsernameForm() {
        String viewName = userController.showChangeUsernameForm();

        assertEquals("change-username", viewName);
    }

    @Test
    public void testChangeUsername_Success() {
        String newUsername = "newUser";
        String password = "password";
        User user = new User();
        user.setPassword("encodedPassword");

        when(userSession.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(userService.changeUsername(user, newUsername)).thenReturn(true);

        String viewName = userController.changeUsername(newUsername, password, model);

        assertEquals("redirect:/profile", viewName);
        verify(userService).changeUsername(user, newUsername);
    }

    @Test
    public void testChangeUsername_Failure() {
        String newUsername = "newUser";
        String password = "wrongPassword";
        User user = new User();
        user.setPassword("encodedPassword");

        when(userSession.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        String viewName = userController.changeUsername(newUsername, password, model);

        assertEquals("change-username", viewName);
        verify(model).addAttribute("error", true);
    }
}
