package bg.softuni.athleticprogramapplication.controller.rest;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.ProgramService;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/programs")
public class ProgramRestController{

    private final ProgramService programService;
    private final UserSession userSession;
    private final UserService userService;
    private final UserRepository userRepository;

    public ProgramRestController(ProgramService programService, UserSession userSession, UserService userService, UserRepository userRepository) {
        this.programService = programService;
        this.userSession = userSession;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/choose")
    public ResponseEntity<Map<String, Object>> chooseProgram(@RequestBody Map<String, Long> request) {
        Map<String, Object> response = new HashMap<>();
        Long programId = request.get("programId");

        if (!userSession.isLoggedIn()) {
            response.put("redirect", "/users/login");
            return ResponseEntity.ok(response);
        }

        Long userId = userSession.getId();
        Optional<User> userById = userRepository.findById(userId);

        if (userById.isPresent() && userById.get().getProgram() != null) {
            response.put("alreadyChosen", true);
        } else if (userById.isPresent()) {
            Program program = programService.getProgramDetailsById(programId);
            if (program != null) {
                User user = userById.get();
                user.setProgram(program);
                userService.save(user);
                userSession.setCurrentUser(user);
                response.put("redirect", "/my-program");
            }
        } else {
            response.put("redirect", "/programs");
        }

        return ResponseEntity.ok(response);
    }
}
