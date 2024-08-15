package serviceimpl;

import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRunBindingModel;
import bg.softuni.athleticprogramapplication.repositories.RunRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.RunServiceImpl;
import bg.softuni.athleticprogramapplication.config.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RunServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

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
    void testAddRun_Success() {
        AddRunBindingModel addRun = new AddRunBindingModel();
        // Set the properties of addRun...

        when(restTemplate.postForEntity(anyString(), eq(addRun), eq(Run.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        boolean result = runService.addRun(addRun);

        assertTrue(result);
    }

    @Test
    void testFindRunsByUserId() {
        Run[] runs = new Run[]{new Run(), new Run()};
        when(restTemplate.getForObject(anyString(), eq(Run[].class))).thenReturn(runs);

        List<Run> result = runService.findRunsByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void testRemoveRun() {
        doNothing().when(restTemplate).delete(anyString());

        runService.removeRun(1L, 1L);

        verify(restTemplate, times(1)).delete(anyString());
    }

    @Test
    void testGetRunByIdAndUserId() {
        Run run = new Run();
        when(restTemplate.getForObject(anyString(), eq(Run.class))).thenReturn(run);

        Run result = runService.getRunByIdAndUserId(1L, 1L);

        assertNotNull(result);
    }

    @Test
    void testConvertToAddRunDTO() {
        Run run = new Run();
        AddRunBindingModel addRun = new AddRunBindingModel();
        when(modelMapper.map(run, AddRunBindingModel.class)).thenReturn(addRun);

        AddRunBindingModel result = runService.convertToAddRunDTO(run);

        assertEquals(addRun, result);
    }

    @Test
    void testGetTotalTime() {
        Run run1 = new Run();
        run1.setHours(1);
        run1.setMinutes(30);
        run1.setSeconds(20);

        Run run2 = new Run();
        run2.setHours(2);
        run2.setMinutes(15);
        run2.setSeconds(40);

        List<Run> runs = Arrays.asList(run1, run2);

        String totalTime = runService.getTotalTime(runs);

        assertEquals("3h 46m 0s", totalTime);
    }

    @Test
    void testGetTotalDistance() {
        Run run1 = new Run();
        run1.setKilometers(5);
        run1.setMeters(300);

        Run run2 = new Run();
        run2.setKilometers(3);
        run2.setMeters(700);

        List<Run> runs = Arrays.asList(run1, run2);

        String totalDistance = runService.getTotalDistance(runs);

        assertEquals("9 km 0 m", totalDistance);
    }

    @Test
    void testEditRun() {
        Run run = new Run();
        run.setUser(new User());
        run.getUser().setId(1L);

        when(restTemplate.getForObject(anyString(), eq(Run.class))).thenReturn(run);

        AddRunBindingModel addRun = new AddRunBindingModel();
        // Set the properties of addRun...

        runService.editRun(1L, addRun, 1L);

        verify(restTemplate, times(1)).put(anyString(), eq(run));
    }

    @Test
    void testNormalizeTime() {
        AddRunBindingModel addRun = new AddRunBindingModel();
        addRun.setSeconds(125);
        addRun.setMinutes(70);
        addRun.setHours(1);

        runService.addRun(addRun);

        assertEquals(5, addRun.getSeconds());
        assertEquals(12, addRun.getMinutes());
        assertEquals(2, addRun.getHours());
    }
}
