package serviceimpl;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.service.impl.ProgramServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramServiceImpl programService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnAllPrograms() {
        List<Program> expectedPrograms = List.of(new Program(), new Program());
        when(programRepository.findAll()).thenReturn(expectedPrograms);

        List<Program> actualPrograms = programService.findAll();

        assertEquals(expectedPrograms, actualPrograms);
        verify(programRepository, times(1)).findAll();
    }

    @Test
    void getProgramDetailsById_ShouldReturnProgram_WhenIdExists() {
        Long programId = 1L;
        Program expectedProgram = new Program();
        when(programRepository.findById(programId)).thenReturn(Optional.of(expectedProgram));

        Program actualProgram = programService.getProgramDetailsById(programId);

        assertEquals(expectedProgram, actualProgram);
        verify(programRepository, times(1)).findById(programId);
    }

    @Test
    void getProgramDetailsById_ShouldReturnNull_WhenIdDoesNotExist() {
        Long programId = 1L;
        when(programRepository.findById(programId)).thenReturn(Optional.empty());

        Program actualProgram = programService.getProgramDetailsById(programId);

        assertNull(actualProgram);
        verify(programRepository, times(1)).findById(programId);
    }

    @Test
    void findByProgramGoal_ShouldReturnProgram_WhenGoalExists() {
        ProgramGoal goal = ProgramGoal.LOSE_WEIGHT;
        Program expectedProgram = new Program();
        when(programRepository.findByProgramGoal(goal)).thenReturn(Optional.of(expectedProgram));

        Program actualProgram = programService.findByProgramGoal("LOSE_WEIGHT");

        assertEquals(expectedProgram, actualProgram);
        verify(programRepository, times(1)).findByProgramGoal(goal);
    }

    @Test
    void findByProgramGoal_ShouldReturnNull_WhenGoalDoesNotExist() {
        ProgramGoal goal = ProgramGoal.LOSE_WEIGHT;
        when(programRepository.findByProgramGoal(goal)).thenReturn(Optional.empty());

        Program actualProgram = programService.findByProgramGoal("LOSE_WEIGHT");

        assertNull(actualProgram);
        verify(programRepository, times(1)).findByProgramGoal(goal);
    }

    @Test
    void findById_ShouldReturnProgram_WhenIdExists() {
        Long programId = 1L;
        Program expectedProgram = new Program();
        when(programRepository.findById(programId)).thenReturn(Optional.of(expectedProgram));

        Program actualProgram = programService.findById(programId);

        assertEquals(expectedProgram, actualProgram);
        verify(programRepository, times(1)).findById(programId);
    }

    @Test
    void findById_ShouldReturnNull_WhenIdDoesNotExist() {
        Long programId = 1L;
        when(programRepository.findById(programId)).thenReturn(Optional.empty());

        Program actualProgram = programService.findById(programId);

        assertNull(actualProgram);
        verify(programRepository, times(1)).findById(programId);
    }
}
