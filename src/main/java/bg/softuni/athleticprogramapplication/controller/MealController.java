package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.service.MealService;
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
import java.util.Set;

@Controller
public class MealController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private final MealService mealService;
    private final MealRepository mealRepository;
    private final UserSession userSession;

    public MealController(MealService mealService, MealRepository mealRepository, UserSession userSession, UserSession userSession1) {
        this.mealService = mealService;
        this.mealRepository = mealRepository;
        this.userSession = userSession1;
    }

    @ModelAttribute("mealAddBindingModel")
    public MealAddBindingModel addMealBindingModel() {
        return new MealAddBindingModel();
    }

   /* @GetMapping("/add-meal")
    public String addMeal() {
        return "add-meal";
    }

    @PostMapping("/add-meal")
    public String doAddMeal(@Valid MealAddBindingModel mealAddBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        final String attributeName = "mealAddBindingModel";
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("mealData", mealAddBindingModel);
            redirectAttributes.addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/add-meal";
        }

        boolean success = mealService.create(mealAddBindingModel);
        if (!success) {
            redirectAttributes.addFlashAttribute("mealData", bindingResult);
            return "redirect:/add-meal";
        }
        return "redirect:/meals";
    }*/

    @GetMapping("/meals")
    public String getAllMeals(Model model) {
        Long userId = userSession.getId();
        List<Meal> meals = mealService.getAllMeals();
        List<Long> favoriteMeals = mealService.getFavoriteMealsIds(userId);
        model.addAttribute("meals", meals);
        model.addAttribute("favoriteMealIds", favoriteMeals);
        return "mealsList";

    }


    @GetMapping("/meals/{id}")
    public String getMealInfo(@PathVariable Long id, Model model) {
        Meal meal = mealService.getMealById(id);
        model.addAttribute("meal", meal);
        return "meal-details";

    }

    @PostMapping("/meals/{mealId}/favorite")
    public String addToFavorite(@PathVariable Long mealId, RedirectAttributes redirectAttributes) {
        if (!userSession.isLoggedIn()) {
            return "redirect:/users/login";
        }

        Long userId = userSession.getId();
        boolean success = mealService.addFavoriteMeal(userId, mealId);

        if (!success) {
            redirectAttributes.addFlashAttribute("error", "Meal could not be added to favorites");
        } else {
            redirectAttributes.addFlashAttribute("success", "Meal added to favorites!");
        }

        return "redirect:/meals";
    }

    @GetMapping("/users/favorites")
    public String getFavoriteMeals(Model model){
        Long userId = userSession.getId();
        Set<Meal> favoriteMeals = mealService.getFavoriteMeals(userId);
        model.addAttribute("favoriteMeals", favoriteMeals);
        return "user-favorite-meals";

    }


    @PostMapping("/meals/unfavorite/{mealId}")
    public String removeFavoriteMeal(@PathVariable Long mealId, RedirectAttributes redirectAttributes) {
        Long userId = userSession.getId();
        mealService.removeFavoriteMeal(userId, mealId);
        return "redirect:/users/favorites";
    }
}
