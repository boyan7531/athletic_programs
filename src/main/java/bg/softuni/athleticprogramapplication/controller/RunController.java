package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Run;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddRun;
import bg.softuni.athleticprogramapplication.repositories.RunRepository;
import bg.softuni.athleticprogramapplication.service.RunService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RunController {

    private final RunService runService;
    private final UserSession userSession;
    private final RunRepository runRepository;

    public RunController(RunService runService, UserSession userSession, RunRepository runRepository) {
        this.runService = runService;
        this.userSession = userSession;
        this.runRepository = runRepository;
    }

    @GetMapping("/add-run")
    public String addRun(){
        return "add-run";
    }
    @ModelAttribute("addRun")
    public AddRun addRunDTO(){
        return new AddRun();
    }

    @PostMapping("/add-run")
    public String doAddRun(AddRun addRun,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("addRunError", addRun);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addRun", bindingResult);
            return "redirect:/add-run";
        }

        boolean success = runService.addRun(addRun);
        if(!success){
            redirectAttributes.addFlashAttribute("loginError", addRun);
            return "redirect:/add-run";
        }

        return "redirect:/users/run";


    }
    @GetMapping("/users/run")
    public String getUserRuns(Model model){
        if(!userSession.isLoggedIn()){
            return "redirect:/";
        }

        Long userId = userSession.getId();
        if (userId != null && userId != 0) {
            List<Run> runs = runService.findRunsByUserId(userId);
            String totalTime = runService.getTotalTime(runs);
            String totalDistance = runService.getTotalDistance(runs);

            model.addAttribute("runs", runs);
            model.addAttribute("totalTime", totalTime);
            model.addAttribute("totalDistance", totalDistance);
        }
        return "user-runs";
    }

    @PostMapping("/runs/delete/{id}")
    public String deleteRun(@PathVariable Long id){
        Long userId = userSession.getId();
        runService.removeRun(id,userId);
        return "redirect:/users/run";

    }
    @GetMapping("/runs/edit/{id}")
    public String showEditForm(@PathVariable("id")Long id,Model model){
        Long userId = userSession.getId();
        Run run = runService.getRunByIdAndUserId(id, userId);
        if (run != null) {
            AddRun addRun = runService.convertToAddRunDTO(run);
            model.addAttribute("addRun", addRun);
            return "edit-run";
        }
        return "redirect:/users/run";
}
    @PostMapping("/runs/edit/{id}")
    public String editRun(@PathVariable("id") Long id, Model model, @ModelAttribute AddRun addRun){
        Long userId = userSession.getId();
        runService.editRun(id,addRun, userId);
        return "redirect:/users/run";
    }


}


