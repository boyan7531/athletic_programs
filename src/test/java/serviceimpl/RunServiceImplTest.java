package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRun;
import bg.softuni.athleticprogramapplication.repositories.RunRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RunServiceImplTest {

    @Mock
    private RunRepository runRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserSession userSession;

    @InjectMocks
    private RunServiceImpl runService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRun() {
        AddRun addRun = new AddRun();
        addRun.setTitle("Morning Run");
        addRun.setSeconds(120);
        addRun.setMinutes(61);
        addRun.setHours(1);
        addRun.setMeters(1500);
        addRun.setKilometers(2);

        User user = new User();
        user.setId(1L);

        when(userSession.getId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        boolean result = runService.addRun(addRun);

        assertTrue(result);
        verify(runRepository, times(1)).save(any(Run.class));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testFindRunsByUserId() {
        Run run1 = new Run();
        run1.setId(1L);
        Run run2 = new Run();
        run2.setId(2L);
        when(runRepository.findByUserId(1L)).thenReturn(List.of(run1, run2));

        List<Run> runs = runService.findRunsByUserId(1L);

        assertNotNull(runs);
        assertEquals(2, runs.size());
        verify(runRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testRemoveRun() {
        User user = new User();
        user.setId(1L);
        Run run = new Run();
        run.setId(1L);

        user.getRuns().add(run);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(runRepository.findById(1L)).thenReturn(run);

        runService.removeRun(1L, 1L);

        verify(runRepository, times(1)).delete(run);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetRunByIdAndUserId() {
        User user = new User();
        user.setId(1L);
        Run run = new Run();
        run.setId(1L);

        user.getRuns().add(run);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(runRepository.findById(1L)).thenReturn(run);

        Run foundRun = runService.getRunByIdAndUserId(1L, 1L);

        assertNotNull(foundRun);
        assertEquals(1L, foundRun.getId());
        verify(userRepository, times(1)).findById(1L);
        verify(runRepository, times(1)).findById(1L);
    }

    @Test
    void testConvertToAddRunDTO() {
        Run run = new Run();
        run.setId(1L);
        run.setTitle("Morning Run");
        AddRun addRun = new AddRun();
        addRun.setTitle("Morning Run");

        when(modelMapper.map(run, AddRun.class)).thenReturn(addRun);

        AddRun convertedRun = runService.convertToAddRunDTO(run);

        assertNotNull(convertedRun);
        assertEquals("Morning Run", convertedRun.getTitle());
        verify(modelMapper, times(1)).map(run, AddRun.class);
    }

    @Test
    void testEditRun() {
        AddRun addRun = new AddRun();
        addRun.setTitle("Morning Run");
        addRun.setSeconds(120);
        addRun.setMinutes(61);
        addRun.setHours(1);
        addRun.setMeters(1500);
        addRun.setKilometers(2);

        User user = new User();
        user.setId(1L);
        Run run = new Run();
        run.setId(1L);

        user.getRuns().add(run);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(runRepository.findById(1L)).thenReturn(run);

        runService.editRun(1L, addRun, 1L);

        verify(runRepository, times(1)).save(run);
        verify(modelMapper, times(1)).map(addRun, run);
    }
}
