package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;

import java.util.List;

public interface ProgramService {
    List<Program> findAll();



    Program getProgramDetailsById(Long id);


    Program findByProgramGoal(String loseWeight);

    Program findById(Long programId);

}
