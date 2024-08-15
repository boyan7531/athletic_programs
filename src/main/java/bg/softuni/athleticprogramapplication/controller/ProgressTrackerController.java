package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.ProgressTracker;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.AddProgressBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.ProgressTrackerService;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProgressTrackerController {

    private final UserService userService;
    private final ProgressTrackerService progressTrackerService;
    private final UserSession userSession;
    private final UserRepository userRepository;

    public ProgressTrackerController(UserService userService, ProgressTrackerService progressTrackerService, UserSession userSession, UserRepository userRepository) {
        this.userService = userService;
        this.progressTrackerService = progressTrackerService;
        this.userSession = userSession;
        this.userRepository = userRepository;
    }


    @GetMapping("/progress")
    public String viewProgress(Model model){
        User currentUser = userSession.getCurrentUser();

        if (currentUser == null) {
            return "redirect:/users/login";
        }

        List<ProgressTracker> progressList = progressTrackerService.getProgressForUserOrderByDateDesc(currentUser.getId());
        model.addAttribute("progressList", progressList);

        // Add summary data
        double currentWeight = progressTrackerService.getCurrentWeight(currentUser.getId());
        double bodyFatPercentage = progressTrackerService.getCurrentBodyFatPercentage(currentUser.getId());

        model.addAttribute("currentWeight", currentWeight);
        model.addAttribute("bodyFatPercentage", bodyFatPercentage);

        return "progress-tracker";
    }


    @GetMapping("/add-progress")
    public String addProgress(){
        return "add-progress";
    }
    @ModelAttribute("AddProgressBindingModel")
    public AddProgressBindingModel getAddProgressBindingModel(){
        return new AddProgressBindingModel();
    }


    @PostMapping("/add-progress")
    public String doAddProgress(@Valid AddProgressBindingModel addProgressBindingModel,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addProgressBindingModel", addProgressBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.AddProgressBindingModel", bindingResult);
            return "redirect:/add-progress";
        }

        boolean success = progressTrackerService.addProgress(addProgressBindingModel);
        if(!success){
            redirectAttributes.addFlashAttribute("progressAddError", bindingResult);
            return "redirect:/add-progress";
        }
        return "redirect:/progress";
    }

    @PostMapping("/progress/delete/{id}")
    public String deleteProgress(@PathVariable("id") Long id){
        Long userId = userSession.getId();
        progressTrackerService.remove(id, userId);
        return "redirect:/progress";
    }

}
