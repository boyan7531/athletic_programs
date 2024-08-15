package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Program;
import bg.softuni.athleticprogramapplication.entities.ProgramGoal;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.ProgramService;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ProgramController {
    private final ProgramService programService;
    private final UserSession userSession;
    private final UserService userService;
    private final UserRepository userRepository;

    public ProgramController(ProgramService programService, UserSession userSession, UserService userService, UserRepository userRepository) {
        this.programService = programService;
        this.userSession = userSession;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/programs")
    public String getPrograms(Model model) {
        List<Program> programs = programService.findAll();
        model.addAttribute("programs", programs);
        return "programs";
    }

//    @PostMapping("/choose-program")
//    @ResponseBody
//    public Map<String, Object> chooseProgram(@RequestParam("programId") Long programId) {
//        Map<String, Object> response = new HashMap<>();
//
//        if (!userSession.isLoggedIn()) {
//            response.put("redirect", "/users/login");
//            return response;
//        }
//
//        Long userId = userSession.getId();
//        Optional<User> userById = userRepository.findById(userId);
//        Program program = programService.getProgramDetailsById(programId);
//
//        if (userById.isPresent() && userById.get().getProgram() != null) {
//            response.put("alreadyChosen", true);
//        } else if (userById.isPresent() && program != null) {
//            User user = userById.get();
//            user.setProgram(program);
//            userService.save(user);
//            userSession.setCurrentUser(user);
//            response.put("alreadyChosen", false);
//        }
//
//        return response;
//    }

    @PostMapping("/remove-program")
    public String removeProgram() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }

        Long userId = userSession.getId();
        Optional<User> userById = userRepository.findById(userId);

        if (userById.isPresent()) {
            User user = userById.get();
            user.setProgram(null);
            userService.save(user);
            userSession.setCurrentUser(user);
            return "redirect:/programs";
        }

        return "redirect:/error";
    }

    @GetMapping("/programs/lose-weight")
    public String loseWeight(Model model) {
        Program program = programService.findByProgramGoal(ProgramGoal.LOSE_WEIGHT.toString());
        if (program == null) {
            return "redirect:/error";
        }
        model.addAttribute("programId", program.getId());
        return "lose-weight-details";
    }

    @GetMapping("/programs/run-more")
    public String runMore(Model model) {
        Program program = programService.findByProgramGoal(ProgramGoal.RUN_MORE.toString());
        if (program == null) {
            return "redirect:/error";
        }
        model.addAttribute("programId", program.getId());
        return "run-more-details";
    }

    @GetMapping("/my-program")
    public String myProgram(Model model) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }

        Long userId = userSession.getId();
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent() && userById.get().getProgram() != null) {
            Program program = userById.get().getProgram();
            model.addAttribute("program", program);

            if (program.getProgramGoal().equals(ProgramGoal.LOSE_WEIGHT)) {
                return "lose-weight-program";
            } else if (program.getProgramGoal().equals(ProgramGoal.RUN_MORE)) {
                return "run-more-program";
            }
        }

        return "redirect:/programs";
    }
}
