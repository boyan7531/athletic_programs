package bg.softuni.athleticprogramapplication.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String ingredients;
    @Column(nullable = false,columnDefinition = "TEXT")
    private String recipe;

    @Column(nullable = false, name = "title_bg")
    private String titleBg;
    @Column(nullable = false, columnDefinition = "TEXT", name = "ingredients_bg")
    private String ingredientsBg;
    @Column(nullable = false, columnDefinition = "TEXT",name = "recipe_bg")
    private String recipeBg;

    @Column(nullable = false)
    private Integer calories;

    @Column(nullable = false)
    private String protein;

    @ManyToMany(mappedBy = "favoriteMeals")
    private Set<User> users;

    @Column
    private String imageUrl;

    public Meal() {
        this.users = new HashSet<>();
    }


    public Meal(String titleEn, String titleBg, int calories, int protein,
                String ingredientsEn, String ingredientsBg,
                String recipeEn, String recipeBg, String imageUrl) {
        this.title = titleEn;
        this.titleBg = titleBg;
        this.calories = calories;
        this.protein = String.valueOf(protein);
        this.ingredients = ingredientsEn;
        this.ingredientsBg = ingredientsBg;
        this.recipe = recipeEn;
        this.recipeBg = recipeBg;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titleEn) {
        this.title = titleEn;
    }

    public String getTitleBg() {
        return titleBg;
    }

    public void setTitleBg(String titleBg) {
        this.titleBg = titleBg;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredientsEn) {
        this.ingredients = ingredientsEn;
    }

    public String getIngredientsBg() {
        return ingredientsBg;
    }

    public void setIngredientsBg(String ingredientsBg) {
        this.ingredientsBg = ingredientsBg;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipeEn) {
        this.recipe = recipeEn;
    }

    public String getRecipeBg() {
        return recipeBg;
    }

    public void setRecipeBg(String recipeBg) {
        this.recipeBg = recipeBg;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> usersWhoFavorited) {
        this.users = usersWhoFavorited;
    }
}

