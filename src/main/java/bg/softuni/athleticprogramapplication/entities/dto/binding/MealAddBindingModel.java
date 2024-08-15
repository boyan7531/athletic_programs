package bg.softuni.athleticprogramapplication.entities.dto.binding;

import bg.softuni.athleticprogramapplication.entities.MealType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MealAddBindingModel {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    @NotNull
    private Integer calories;
    @NotNull
    private String ingredients;
    @NotNull
    private String recipe;
    @NotNull
    private MealType mealType;

    public MealAddBindingModel() {
    }


    public @NotNull String getIngredients() {
        return ingredients;
    }

    public void setIngredients(@NotNull String ingredients) {
        this.ingredients = ingredients;
    }

    public @NotNull String getRecipe() {
        return recipe;
    }

    public void setRecipe(@NotNull String recipe) {
        this.recipe = recipe;
    }

    public @NotNull @Size(min = 2, max = 50) String getName() {
        return name;
    }

    public void setName(@NotNull @Size(min = 2, max = 50) String name) {
        this.name = name;
    }

    public @NotNull Integer getCalories() {
        return calories;
    }

    public void setCalories(@NotNull Integer calories) {
        this.calories = calories;
    }

    public @NotNull MealType getMealType() {
        return mealType;
    }

    public void setMealType(@NotNull MealType mealType) {
        this.mealType = mealType;
    }
}
