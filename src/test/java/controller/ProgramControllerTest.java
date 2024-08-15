package controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.controller.ProgramController;
import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.ProgramService;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProgramControllerTest {

    @Mock
    private ProgramService programService;

    @Mock
    private UserSession userSession;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Model model;

    @InjectMocks
    private ProgramController programController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPrograms() {
        // Arrange
        Program program1 = new Program();
        Program program2 = new Program();
        List<Program> programs = Arrays.asList(program1, program2);

        when(programService.findAll()).thenReturn(programs);

        // Act
        String viewName = programController.getPrograms(model);

        // Assert
        assertEquals("programs", viewName);
        verify(model).addAttribute("programs", programs);
    }

    @Test
    public void testRemoveProgram_UserNotLoggedIn() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(false);

        // Act
        String viewName = programController.removeProgram();

        // Assert
        assertEquals("redirect:/users/login", viewName);
    }

    @Test
    public void testRemoveProgram_UserLoggedInAndHasProgram() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(true);
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setProgram(new Program());

        when(userSession.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        String viewName = programController.removeProgram();

        // Assert
        assertEquals("redirect:/programs", viewName);
        verify(userService).save(user);
        verify(userSession).setCurrentUser(user);
    }

    @Test
    public void testRemoveProgram_UserLoggedInButNotFound() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(true);
        Long userId = 1L;

        when(userSession.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        String viewName = programController.removeProgram();

        // Assert
        assertEquals("redirect:/error", viewName);
    }

    @Test
    public void testMyProgram_LoseWeight() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(true);
        Long userId = 1L;
        User user = new User();
        Program program = new Program();
        program.setProgramGoal(ProgramGoal.LOSE_WEIGHT);
        user.setProgram(program);

        when(userSession.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        String viewName = programController.myProgram(model);

        // Assert
        assertEquals("lose-weight-program", viewName);
        verify(model).addAttribute("program", program);
    }

    @Test
    public void testMyProgram_RunMore() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(true);
        Long userId = 1L;
        User user = new User();
        Program program = new Program();
        program.setProgramGoal(ProgramGoal.RUN_MORE);
        user.setProgram(program);

        when(userSession.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        String viewName = programController.myProgram(model);

        // Assert
        assertEquals("run-more-program", viewName);
        verify(model).addAttribute("program", program);
    }

    @Test
    public void testMyProgram_UserNotLoggedIn() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(false);

        // Act
        String viewName = programController.myProgram(model);

        // Assert
        assertEquals("redirect:/users/login", viewName);
    }

    @Test
    public void testMyProgram_UserHasNoProgram() {
        // Arrange
        when(userSession.isLoggedIn()).thenReturn(true);
        Long userId = 1L;
        User user = new User();

        when(userSession.getId()).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        String viewName = programController.myProgram(model);

        // Assert
        assertEquals("redirect:/programs", viewName);
    }
}
