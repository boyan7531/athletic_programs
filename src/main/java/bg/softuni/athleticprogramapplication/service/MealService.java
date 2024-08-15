package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;

import java.util.List;
import java.util.Set;

public interface MealService {
    boolean create(MealAddBindingModel mealAddBindingModel);

    List<Meal> getAllMeals();

    Meal getMealById(Long id);

    List<Long> getFavoriteMealsIds(Long userId);

    boolean addFavoriteMeal(Long userId, Long mealId);

    Set<Meal> getFavoriteMeals(Long userId);

    boolean removeFavoriteMeal(Long userId, Long mealId);


}
