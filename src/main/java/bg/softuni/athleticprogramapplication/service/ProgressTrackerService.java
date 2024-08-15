package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.ProgressTracker;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddProgressBindingModel;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProgressTrackerService {
    List<ProgressTracker> getProgressForUser(Long id);

    double getCurrentWeight(Long id);

    double getCurrentBodyFatPercentage(Long id);


    boolean addProgress(AddProgressBindingModel addProgressBindingModel);

    List<ProgressTracker> getProgressForUserOrderByDateDesc(Long id);

    void remove(Long id, Long userId);
}
