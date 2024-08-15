package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRunBindingModel;

import java.util.List;

public interface RunService {
    boolean addRun(AddRunBindingModel addRun);

    List<Run> findRunsByUserId(Long userId);

    void removeRun(Long id, Long userId);

    void editRun(Long id, AddRunBindingModel addRun, Long userId);

    Run getRunByIdAndUserId(Long id, Long userId);
    AddRunBindingModel convertToAddRunDTO(Run run);

    String getTotalTime(List<Run> runs);

    String getTotalDistance(List<Run> runs);

}
