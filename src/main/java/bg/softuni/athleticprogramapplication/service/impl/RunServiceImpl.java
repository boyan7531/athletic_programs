package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRun;
import bg.softuni.athleticprogramapplication.repositories.RunRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.RunService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RunServiceImpl implements RunService {
    private final RunRepository runRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;

    public RunServiceImpl(RunRepository runRepository, UserRepository userRepository, ModelMapper modelMapper, UserSession userSession) {
        this.runRepository = runRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
    }


    @Override
    public boolean addRun(AddRun addRun) {
        int seconds  = addRun.getSeconds();
        int minutes = addRun.getMinutes();
        int hours = addRun.getHours();
        int meters = addRun.getMeters();
        int kilometers = addRun.getKilometers();
        if(seconds > 60 && seconds %  60 != 0 ){
            minutes += seconds / 60;
            seconds = seconds % 60;
        }
        if(minutes > 60 && minutes %  60 != 0 ){
            hours += minutes / 60;
            minutes = minutes % 60;
        }

        if(meters > 1000 && meters %  1000 != 0 ){
            kilometers += meters / 1000;
            meters = meters % 1000;
        }
//        Run run = this.modelMapper.map(addRun, Run.class);
        Run run = new Run();
        run.setSeconds(seconds);
        run.setHours(hours);
        run.setMinutes(minutes);
        run.setTitle(addRun.getTitle());
        run.setKilometers(kilometers);
        run.setMeters(meters);

        long currentUserId = userSession.getId();
        User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("User not found"));
        run.setUser(currentUser);
        this.runRepository.save(run);
        return true;
    }

    @Override
    public List<Run> findRunsByUserId(Long userId) {
        return runRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void removeRun(Long id, Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        if(userById.isPresent()){
            User user = userById.get();
            Run run = runRepository.findById(id);
            if(user.getRuns().contains(run)){
                user.getRuns().remove(run);
                runRepository.delete(run);
                userRepository.save(user);
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Run getRunByIdAndUserId(Long id, Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        if(userById.isPresent()){
            User user = userById.get();
            Run run = runRepository.findById(id);
            if(user.getRuns().contains(run)){
                return run;
            }
        }
        return null; //!
    }

    @Override
    public AddRun convertToAddRunDTO(Run run) {
        return modelMapper.map(run, AddRun.class);
    }

    @Override
    public String getTotalTime(List<Run> runs) {
        int totalSeconds = runs.stream().mapToInt(run -> run.getHours() * 3600 + run.getMinutes() * 60 + run.getSeconds()).sum();
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%dh %dm %ds", hours, minutes, seconds);
    }

    @Override
    public String getTotalDistance(List<Run> runs) {
        int totalMeters = runs.stream().mapToInt(run -> run.getKilometers() * 1000 + run.getMeters()).sum();
        int kilometers = totalMeters / 1000;
        int meters = totalMeters % 1000;
        return String.format("%d km %d m", kilometers, meters);
    }

    @Override
    @Transactional
    public void editRun(Long id,AddRun addRun, Long userId) {

        Optional<User> userById = userRepository.findById(userId);
        if(userById.isPresent()){
            User user = userById.get();
            Run run = runRepository.findById(id);
            if(user.getRuns().contains(run)){
                int kilometers = run.getKilometers();
                int meters = run.getMeters();
                int hours = run.getHours();
                int minutes = run.getMinutes();
                int seconds = run.getSeconds();
                if(seconds > 60 && seconds %  60 != 0 ){
                    minutes += seconds / 60;
                    seconds = seconds % 60;
                }
                if(minutes > 60 && minutes %  60 != 0 ){
                    hours += minutes / 60;
                    minutes = minutes % 60;
                }

                if(meters > 1000 && meters %  1000 != 0 ){
                    kilometers += meters / 1000;
                    meters = meters % 1000;
                }
                run.setSeconds(seconds);
                run.setHours(hours);
                run.setMinutes(minutes);
                run.setTitle(addRun.getTitle());
                run.setKilometers(kilometers);
                run.setMeters(meters);
                runRepository.save(run);
            }
        }
    }
}
