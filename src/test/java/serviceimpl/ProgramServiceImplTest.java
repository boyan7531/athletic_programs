package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.service.ProgramService;
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
    void testFindAll() {
        Program program1 = new Program();
        program1.setId(1L);
        Program program2 = new Program();
        program2.setId(2L);
        when(programRepository.findAll()).thenReturn(List.of(program1, program2));

        List<Program> programs = programService.findAll();

        assertNotNull(programs);
        assertEquals(2, programs.size());
        verify(programRepository, times(1)).findAll();
    }

    @Test
    void testGetProgramDetailsById() {
        Program program = new Program();
        program.setId(1L);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));

        Program foundProgram = programService.getProgramDetailsById(1L);

        assertNotNull(foundProgram);
        assertEquals(1L, foundProgram.getId());
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProgramDetailsByIdNotFound() {
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        Program foundProgram = programService.getProgramDetailsById(1L);

        assertNull(foundProgram);
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByProgramGoal() {
        Program program = new Program();
        program.setId(1L);
        program.setProgramGoal(ProgramGoal.WEIGHT_LOSS);
        when(programRepository.findByProgramGoal(ProgramGoal.WEIGHT_LOSS)).thenReturn(Optional.of(program));

        Program foundProgram = programService.findByProgramGoal("WEIGHT_LOSS");

        assertNotNull(foundProgram);
        assertEquals(ProgramGoal.WEIGHT_LOSS, foundProgram.getProgramGoal());
        verify(programRepository, times(1)).findByProgramGoal(ProgramGoal.WEIGHT_LOSS);
    }

    @Test
    void testFindByProgramGoalNotFound() {
        when(programRepository.findByProgramGoal(ProgramGoal.WEIGHT_LOSS)).thenReturn(Optional.empty());

        Program foundProgram = programService.findByProgramGoal("WEIGHT_LOSS");

        assertNull(foundProgram);
        verify(programRepository, times(1)).findByProgramGoal(ProgramGoal.WEIGHT_LOSS);
    }

    @Test
    void testFindById() {
        Program program = new Program();
        program.setId(1L);
        when(programRepository.findById(1L)).thenReturn(Optional.of(program));

        Program foundProgram = programService.findById(1L);

        assertNotNull(foundProgram);
        assertEquals(1L, foundProgram.getId());
        verify(programRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(programRepository.findById(1L)).thenReturn(Optional.empty());

        Program foundProgram = programService.findById(1L);

        assertNull(foundProgram);
        verify(programRepository, times(1)).findById(1L);
    }
}
