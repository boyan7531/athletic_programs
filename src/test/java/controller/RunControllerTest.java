package controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.controller.RunController;
import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRunBindingModel;
import bg.softuni.athleticprogramapplication.service.RunService;
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

public class RunControllerTest {

    @Mock
    private RunService runService;

    @Mock
    private UserSession userSession;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private RunController runController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRun_GET() {
        String viewName = runController.addRun();
        assertEquals("add-run", viewName);
    }

    @Test
    public void testDoAddRun_WithErrors() {
        AddRunBindingModel addRun = new AddRunBindingModel();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = runController.doAddRun(addRun, bindingResult, redirectAttributes);

        assertEquals("redirect:/add-run", viewName);
        verify(redirectAttributes).addFlashAttribute("addRunError", addRun);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.addRun", bindingResult);
    }

    @Test
    public void testDoAddRun_Success() {
        AddRunBindingModel addRun = new AddRunBindingModel();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(runService.addRun(addRun)).thenReturn(true);

        String viewName = runController.doAddRun(addRun, bindingResult, redirectAttributes);

        assertEquals("redirect:/users/run", viewName);
        verify(runService).addRun(addRun);
    }

    @Test
    public void testDoAddRun_Failure() {
        AddRunBindingModel addRun = new AddRunBindingModel();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(runService.addRun(addRun)).thenReturn(false);

        String viewName = runController.doAddRun(addRun, bindingResult, redirectAttributes);

        assertEquals("redirect:/add-run", viewName);
        verify(redirectAttributes).addFlashAttribute("loginError", addRun);
    }

    @Test
    public void testGetUserRuns_NotLoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(false);

        String viewName = runController.getUserRuns(model);

        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testGetUserRuns_LoggedIn() {
        when(userSession.isLoggedIn()).thenReturn(true);
        when(userSession.getId()).thenReturn(1L);

        List<Run> runs = new ArrayList<>();
        when(runService.findRunsByUserId(1L)).thenReturn(runs);
        when(runService.getTotalTime(runs)).thenReturn("5 hours");
        when(runService.getTotalDistance(runs)).thenReturn("50 km");

        String viewName = runController.getUserRuns(model);

        assertEquals("user-runs", viewName);
        verify(model).addAttribute("runs", runs);
        verify(model).addAttribute("totalTime", "5 hours");
        verify(model).addAttribute("totalDistance", "50 km");
    }

    @Test
    public void testDeleteRun() {
        when(userSession.getId()).thenReturn(1L);

        String viewName = runController.deleteRun(1L);

        assertEquals("redirect:/users/run", viewName);
        verify(runService).removeRun(1L, 1L);
    }

    @Test
    public void testShowEditForm_RunExists() {
        when(userSession.getId()).thenReturn(1L);
        Run run = new Run();
        when(runService.getRunByIdAndUserId(1L, 1L)).thenReturn(run);
        AddRunBindingModel addRun = new AddRunBindingModel();
        when(runService.convertToAddRunDTO(run)).thenReturn(addRun);

        String viewName = runController.showEditForm(1L, model);

        assertEquals("edit-run", viewName);
        verify(model).addAttribute("addRun", addRun);
    }

    @Test
    public void testShowEditForm_RunNotExists() {
        when(userSession.getId()).thenReturn(1L);
        when(runService.getRunByIdAndUserId(1L, 1L)).thenReturn(null);

        String viewName = runController.showEditForm(1L, model);

        assertEquals("redirect:/users/run", viewName);
    }

    @Test
    public void testEditRun() {
        when(userSession.getId()).thenReturn(1L);
        AddRunBindingModel addRun = new AddRunBindingModel();

        String viewName = runController.editRun(1L, model, addRun);

        assertEquals("redirect:/users/run", viewName);
        verify(runService).editRun(1L, addRun, 1L);
    }
}
