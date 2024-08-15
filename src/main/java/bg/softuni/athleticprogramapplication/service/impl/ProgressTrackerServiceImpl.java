package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.ProgressTracker;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddProgressBindingModel;
import bg.softuni.athleticprogramapplication.repositories.ProgressTrackerRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.ProgressTrackerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgressTrackerServiceImpl implements ProgressTrackerService {
    private final ProgressTrackerRepository progressTrackerRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;
    private final UserRepository userRepository;

    public ProgressTrackerServiceImpl(ProgressTrackerRepository progressTrackerRepository, ModelMapper modelMapper, UserSession userSession, UserRepository userRepository) {
        this.progressTrackerRepository = progressTrackerRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
        this.userRepository = userRepository;
    }


    public List<ProgressTracker> getProgressForUser(Long userId) {
        return progressTrackerRepository.findByUserId(userId);
    }

    public double getCurrentWeight(Long userId) {
        // Assuming the latest progress entry has the current weight
        return progressTrackerRepository.findTopByUserIdOrderByDateDesc(userId)
                .map(ProgressTracker::getWeight)
                .orElse(0.0);
    }

    public double getCurrentBodyFatPercentage(Long userId) {
        return progressTrackerRepository.findTopByUserIdOrderByDateDesc(userId)
                .map(ProgressTracker::getBodyFatPercentage)
                .orElse(0.0);
    }


    @Override
    public boolean addProgress(AddProgressBindingModel addProgressBindingModel) {
        User user = userSession.getCurrentUser();
        ProgressTracker progressTracker = new ProgressTracker();
        progressTracker.setDate(addProgressBindingModel.getDate());
        progressTracker.setWeight(addProgressBindingModel.getWeight());
        progressTracker.setBodyFatPercentage(addProgressBindingModel.getBodyFatPercentage());
        progressTracker.setUser(user);
        this.progressTrackerRepository.saveAndFlush(progressTracker);
        return true;
    }

    @Override
    public List<ProgressTracker> getProgressForUserOrderByDateDesc(Long id) {
        return progressTrackerRepository.findByUserId(id);
    }
    @Transactional
    @Override
    public void remove(Long id, Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        if(userById.isPresent()) {
            User user = userById.get();
            Optional<ProgressTracker> progressTracker = progressTrackerRepository.findById(id);
            if(progressTracker.isPresent()) {
                if(user.getProgressTrackers().contains(progressTracker.get())) {
                    user.getProgressTrackers().remove(progressTracker.get());
                    progressTrackerRepository.delete(progressTracker.get());
                    userRepository.save(user);

                }
            }
        }
    }

    public void saveProgress(ProgressTracker progressTracker) {
        progressTrackerRepository.save(progressTracker);
    }
}
