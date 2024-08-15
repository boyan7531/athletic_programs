package bg.softuni.athleticprogramapplication.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(nullable = false)
    private Integer calories;
    @Column(nullable = false)
    private String protein;

    @Column(nullable = false, length = 5000)
    private String ingredients;
    @Column(nullable = false, length = 5000)
    private String recipe;
    //    @Enumerated(EnumType.STRING)
//    @Column(name = "meal_type",nullable = false)
//    private MealType mealType;
    @ManyToMany(mappedBy = "favoriteMeals")
    @JsonIgnore
    private Set<User> users;
    @Column
    private String imageUrl;


    public Meal() {
        this.users = new HashSet<>();
    }

    public Meal(String title, int calories, int protein, String ingredients, String recipe, String imageUrl) {
        this.title = title;
        this.calories = calories;
        this.protein = String.valueOf(protein);
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.imageUrl = imageUrl;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> usersWhoFavorited) {
        this.users = usersWhoFavorited;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = title;
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

    //    public MealType getMealType() {
//        return mealType;
//    }
//
//    public void setMealType(MealType mealType) {
//        this.mealType = mealType;
//    }
//}
}
