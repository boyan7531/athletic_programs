package bg.softuni.athleticprogramapplication.init;

import bg.softuni.athleticprogramapplication.entities.*;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.repositories.RoleRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class InitService implements CommandLineRunner {
    private final ProgramRepository programRepository;
    private final MealRepository mealRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(ProgramRepository programRepository, MealRepository mealRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.programRepository = programRepository;
        this.mealRepository = mealRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean count = this.programRepository.count() > 0;
        if (count) {
            return;
        } else {
            this.programRepository.save(new Program("Weight Loss", "Select this if you want to lose weight", ProgramGoal.LOSE_WEIGHT, 8));
            this.programRepository.save(new Program("Run More", "Select this if you want to run more", ProgramGoal.RUN_MORE, 8));
        }
        initMeals();

    }

    @PostConstruct
    public void initMeals() {
        if (this.mealRepository.count() == 0) {
            List<Meal> meals = new ArrayList<>();
            meals.add(new Meal("Grilled Chicken Breast", 300, 35, "Chicken breast, olive oil, garlic, rosemary, salt, pepper.", "Marinate chicken breast with olive oil, minced garlic, chopped rosemary, salt, and pepper. Grill on medium-high heat for 6-7 minutes on each side or until fully cooked. Let it rest for a few minutes before serving.", "/images/grilled_chicken_breast.jpg"));
            meals.add(new Meal("Turkey Meatballs", 400, 30, "Ground turkey, breadcrumbs, egg, garlic, onion, parsley, salt, pepper.", "In a bowl, mix ground turkey, breadcrumbs, beaten egg, minced garlic, chopped onion, parsley, salt, and pepper. Form into meatballs. Bake at 375°F for 20 minutes or until fully cooked.", "/images/turkey_meatballs.jpg"));
            meals.add(new Meal("Baked Salmon Fillets", 350, 25, "Salmon fillets, olive oil, lemon, dill, salt, pepper.", "Preheat oven to 400°F. Place salmon fillets on a baking sheet. Drizzle with olive oil, squeeze lemon juice, and sprinkle with chopped dill, salt, and pepper. Bake for 12-15 minutes or until salmon flakes easily with a fork.", "/images/salmon_fillets.jpg"));
            meals.add(new Meal("Steak with Veggies", 600, 40, "Steak, olive oil, garlic, asparagus, bell peppers, salt, pepper.", "Season steak with salt, pepper, and minced garlic. Heat olive oil in a pan over medium-high heat and cook the steak for 4-5 minutes on each side for medium-rare. In the same pan, sauté asparagus and bell peppers until tender. Serve steak with veggies.", "/images/steak_veggies.jpg"));
            meals.add(new Meal("Egg White Omelette", 200, 20, "Egg whites, spinach, mushrooms, tomatoes, olive oil, salt, pepper.", "In a bowl, whisk egg whites with salt and pepper. Heat olive oil in a non-stick pan, add chopped spinach, sliced mushrooms, and diced tomatoes. Pour in the egg whites and cook until set. Fold the omelette and serve hot.", "/images/egg_white_omlette.jpg"));
            meals.add(new Meal("Greek Yogurt Parfait", 250, 15, "Greek yogurt, honey, granola, mixed berries.", "In a glass, layer Greek yogurt, a drizzle of honey, granola, and mixed berries. Repeat layers and serve immediately.", "/images/greek_yogurt_parfait.jpg"));
            meals.add(new Meal("Shrimp Stir Fry", 400, 30, "Shrimp, bell peppers, broccoli, soy sauce, garlic, ginger, olive oil.", "Heat olive oil in a pan, add minced garlic and ginger, and sauté for 1 minute. Add shrimp and cook until pink. Remove shrimp and add chopped bell peppers and broccoli, stir-fry until tender. Return shrimp to the pan, add soy sauce, and cook for another 2 minutes. Serve hot.", "/images/shrimp_stir_fry.jpg"));
            meals.add(new Meal("Tuna Salad", 300, 25, "Canned tuna, Greek yogurt, celery, red onion, lemon juice, salt, pepper.", "In a bowl, mix drained canned tuna with Greek yogurt, finely chopped celery, diced red onion, and a squeeze of lemon juice. Season with salt and pepper. Serve on whole grain bread or with lettuce wraps.", "/images/tuna_salad.jpg"));
            meals.add(new Meal("Cottage Cheese with Fruit", 200, 15, "Cottage cheese, mixed fruit (berries, pineapple, etc.), honey.", "In a bowl, combine cottage cheese with mixed fruit. Drizzle with honey and serve immediately.", "/images/cottage_cheese_fruit.jpg"));

            mealRepository.saveAll(meals);
        }
    }

}
