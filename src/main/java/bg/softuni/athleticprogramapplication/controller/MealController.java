package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.service.MealService;
import bg.softuni.athleticprogramapplication.service.impl.MealServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MealController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private final MealService mealService;
    private final MealRepository mealRepository;

    public MealController(MealService mealService, MealRepository mealRepository) {
        this.mealService = mealService;
        this.mealRepository = mealRepository;
    }

    @ModelAttribute("mealAddBindingModel")
    public MealAddBindingModel addMealBindingModel() {
        return new MealAddBindingModel();
    }

    @GetMapping("/add-meal")
    public String addMeal(){
        return "add-meal";
    }

    @PostMapping("/add-meal")
    public String doAddMeal(@Valid MealAddBindingModel mealAddBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        final String attributeName = "mealAddBindingModel";
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("mealData", mealAddBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName , bindingResult);
            return "redirect:/add-meal";
        }

        boolean success = mealService.create(mealAddBindingModel);
        if(!success){
            redirectAttributes.addFlashAttribute("mealData", bindingResult);
            return "redirect:/add-meal";
        }
        return  "redirect:/";
    }

    @GetMapping("/meals")
    public String getAllMeals(Model model){
       List<Meal> meals = mealService.getAllMeals();
       model.addAttribute("meals", meals);
       return "meals";
    }

}
