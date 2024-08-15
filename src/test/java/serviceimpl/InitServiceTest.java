package serviceimpl;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.init.InitService;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InitServiceTest {

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private MealRepository mealRepository;


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private InitService initService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRun_WhenProgramsExist() throws Exception {
        when(programRepository.count()).thenReturn(1L);

        initService.run();

        verify(programRepository, never()).save(any(Program.class));
    }

    @Test
    void testRun_WhenProgramsDoNotExist() throws Exception {
        when(programRepository.count()).thenReturn(0L);

        initService.run();

        verify(programRepository, times(2)).save(any(Program.class));
    }

    @Test
    void testInitMeals_WhenNoMealsExist() {
        when(mealRepository.count()).thenReturn(0L);

        initService.initMeals();

        verify(mealRepository, times(1)).saveAll(any(List.class));
    }

    @Test
    void testInitMeals_WhenMealsExist() {
        when(mealRepository.count()).thenReturn(1L);

        initService.initMeals();

        verify(mealRepository, never()).saveAll(any(List.class));
    }
}
