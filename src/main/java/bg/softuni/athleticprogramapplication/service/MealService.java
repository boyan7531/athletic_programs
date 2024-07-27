package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;

import java.util.List;

public interface MealService {
    boolean create(MealAddBindingModel mealAddBindingModel);
    List<Meal> getAllMeals();
}
