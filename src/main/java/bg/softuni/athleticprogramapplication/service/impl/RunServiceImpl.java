package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRunBindingModel;
import bg.softuni.athleticprogramapplication.repositories.RunRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.RunService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RunServiceImpl implements RunService {

    private final RestTemplate restTemplate;
    private final String RUN_API_URL = "http://localhost:8081/runs";


    private final RunRepository runRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;

    public RunServiceImpl(RestTemplate restTemplate, RunRepository runRepository, UserRepository userRepository, ModelMapper modelMapper, UserSession userSession) {
        this.restTemplate = restTemplate;
        this.runRepository = runRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
    }


    @Override
    public boolean addRun(AddRunBindingModel addRun) {
        normalizeTime(addRun);
        ResponseEntity<Run> response = restTemplate.postForEntity(RUN_API_URL, addRun, Run.class);

        if (response == null) {
            return false;
        }

        return response.getStatusCode() == HttpStatus.OK;
    }


    @Override
    public List<Run> findRunsByUserId(Long userId) {
        String url = RUN_API_URL + "?userId=" + userId;
        Run[] runs = restTemplate.getForObject(url, Run[].class);
        return Arrays.asList(runs);
    }

    @Override
    @Transactional
    public void removeRun(Long id, Long userId) {
        String url = RUN_API_URL + "/" + id + "?userId=" + userId;
        restTemplate.delete(url);
    }

    @Override
    @Transactional(readOnly = true)
    public Run getRunByIdAndUserId(Long id, Long userId) {
        String url = RUN_API_URL + "/" + id + "?userId=" + userId;
        return restTemplate.getForObject(url, Run.class);
    }

    @Override
    public AddRunBindingModel convertToAddRunDTO(Run run) {
        return modelMapper.map(run, AddRunBindingModel.class);
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
    public void editRun(Long id, AddRunBindingModel addRun, Long userId) {
        String url = RUN_API_URL + "/" + id;
        Run run = restTemplate.getForObject(url, Run.class);

        if (run != null && run.getUser().getId().equals(userId)) {
            // Normalize time fields before updating the run
            normalizeTime(addRun);

            run.setTitle(addRun.getTitle());
            run.setHours(addRun.getHours());
            run.setMinutes(addRun.getMinutes());
            run.setSeconds(addRun.getSeconds());
            run.setKilometers(addRun.getKilometers());
            run.setMeters(addRun.getMeters());

            restTemplate.put(url, run);
        }
    }

    private void normalizeTime(AddRunBindingModel addRun) {
        int seconds = Optional.ofNullable(addRun.getSeconds()).orElse(0);
        int minutes = Optional.ofNullable(addRun.getMinutes()).orElse(0);
        int hours = Optional.ofNullable(addRun.getHours()).orElse(0);

        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds = seconds % 60;
        }

        if (minutes >= 60) {
            hours += minutes / 60;
            minutes = minutes % 60;
        }

        addRun.setSeconds(seconds);
        addRun.setMinutes(minutes);
        addRun.setHours(hours);
    }



}
