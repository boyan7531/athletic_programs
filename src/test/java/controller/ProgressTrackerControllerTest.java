package controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.controller.ProgressTrackerController;
import bg.softuni.athleticprogramapplication.entities.ProgressTracker;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddProgressBindingModel;
import bg.softuni.athleticprogramapplication.service.ProgressTrackerService;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProgressTrackerControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProgressTrackerService progressTrackerService;

    @Mock
    private UserSession userSession;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private ProgressTrackerController progressTrackerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewProgress_UserNotLoggedIn() {
        // Arrange
        when(userSession.getCurrentUser()).thenReturn(null);

        // Act
        String viewName = progressTrackerController.viewProgress(model);

        // Assert
        assertEquals("redirect:/users/login", viewName);
    }

    @Test
    public void testViewProgress_UserLoggedIn() {
        // Arrange
        User user = new User();
        user.setId(1L);
        when(userSession.getCurrentUser()).thenReturn(user);

        List<ProgressTracker> progressList = new ArrayList<>();
        when(progressTrackerService.getProgressForUserOrderByDateDesc(user.getId())).thenReturn(progressList);
        when(progressTrackerService.getCurrentWeight(user.getId())).thenReturn(75.0);
        when(progressTrackerService.getCurrentBodyFatPercentage(user.getId())).thenReturn(20.0);

        // Act
        String viewName = progressTrackerController.viewProgress(model);

        // Assert
        assertEquals("progress-tracker", viewName);
        verify(model).addAttribute("progressList", progressList);
        verify(model).addAttribute("currentWeight", 75.0);
        verify(model).addAttribute("bodyFatPercentage", 20.0);
    }

    @Test
    public void testAddProgress_GetMethod() {
        // Act
        String viewName = progressTrackerController.addProgress();

        // Assert
        assertEquals("add-progress", viewName);
    }

    @Test
    public void testDoAddProgress_WithErrors() {
        // Arrange
        AddProgressBindingModel addProgressBindingModel = new AddProgressBindingModel();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = progressTrackerController.doAddProgress(addProgressBindingModel, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/add-progress", viewName);
        verify(redirectAttributes).addFlashAttribute("addProgressBindingModel", addProgressBindingModel);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.AddProgressBindingModel", bindingResult);
    }

    @Test
    public void testDoAddProgress_Successful() {
        // Arrange
        AddProgressBindingModel addProgressBindingModel = new AddProgressBindingModel();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(progressTrackerService.addProgress(addProgressBindingModel)).thenReturn(true);

        // Act
        String viewName = progressTrackerController.doAddProgress(addProgressBindingModel, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/progress", viewName);
    }

    @Test
    public void testDoAddProgress_Failure() {
        // Arrange
        AddProgressBindingModel addProgressBindingModel = new AddProgressBindingModel();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(progressTrackerService.addProgress(addProgressBindingModel)).thenReturn(false);

        // Act
        String viewName = progressTrackerController.doAddProgress(addProgressBindingModel, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/add-progress", viewName);
        verify(redirectAttributes).addFlashAttribute("progressAddError", bindingResult);
    }

    @Test
    public void testDeleteProgress() {
        // Arrange
        Long progressId = 1L;
        Long userId = 2L;
        when(userSession.getId()).thenReturn(userId);

        // Act
        String viewName = progressTrackerController.deleteProgress(progressId);

        // Assert
        assertEquals("redirect:/progress", viewName);
        verify(progressTrackerService).remove(progressId, userId);
    }
}
