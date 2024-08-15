package bg.softuni.athleticprogramapplication.init;

import bg.softuni.athleticprogramapplication.entities.*;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.ProgramRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitService implements CommandLineRunner {
    private final ProgramRepository programRepository;
    private final MealRepository mealRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public InitService(ProgramRepository programRepository, MealRepository mealRepository,  UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.programRepository = programRepository;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @PostConstruct
    public void initMeals() {
        if (this.mealRepository.count() == 0) {
            List<Meal> meals = new ArrayList<>();

            meals.add(new Meal(
                    "Grilled Chicken Breast",
                    "Пилешко филе на скара",
                    300,
                    35,
                    "Chicken breast, olive oil, garlic, rosemary, salt, pepper.",
                    "Пилешко филе, зехтин, чесън, розмарин, сол, черен пипер.",
                    "Marinate chicken breast with olive oil, minced garlic, chopped rosemary, salt, and pepper. Grill on medium-high heat for 6-7 minutes on each side or until fully cooked. Let it rest for a few minutes before serving.",
                    "Мариновайте пилешкото филе със зехтин, нарязан чесън, нарязан розмарин, сол и черен пипер. Гответе на средно висока температура за 6-7 минути от всяка страна или докато не е напълно готово. Оставете го да почине за няколко минути преди сервиране.",
                    "/images/grilled_chicken_breast.jpg"));

            meals.add(new Meal(
                    "Turkey Meatballs",
                    "Турски кюфтенца",
                    400,
                    30,
                    "Ground turkey, breadcrumbs, egg, garlic, onion, parsley, salt, pepper.",
                    "Смляно пуешко месо, галета, яйце, чесън, лук, магданоз, сол, черен пипер.",
                    "In a bowl, mix ground turkey, breadcrumbs, beaten egg, minced garlic, chopped onion, parsley, salt, and pepper. Form into meatballs. Bake at 375°F for 20 minutes or until fully cooked.",
                    "В купа смесете смляното пуешко месо, галетата, разбитото яйце, нарязания чесън, нарязания лук, магданоза, солта и черния пипер. Оформете кюфтенца. Печете на 375°F (190°C) за 20 минути или докато не са напълно готови.",
                    "/images/turkey_meatballs.jpg"));

            meals.add(new Meal(
                    "Baked Salmon Fillets",
                    "Печено филе от сьомга",
                    350,
                    25,
                    "Salmon fillets, olive oil, lemon, dill, salt, pepper.",
                    "Филе от сьомга, зехтин, лимон, копър, сол, черен пипер.",
                    "Preheat oven to 400°F. Place salmon fillets on a baking sheet. Drizzle with olive oil, squeeze lemon juice, and sprinkle with chopped dill, salt, and pepper. Bake for 12-15 minutes or until salmon flakes easily with a fork.",
                    "Загрейте фурната на 400°F (200°C). Поставете филетата от сьомга на тава за печене. Полейте със зехтин, изстискайте лимонов сок и поръсете с нарязан копър, сол и черен пипер. Печете 12-15 минути или докато сьомгата не се разпада лесно с вилица.",
                    "/images/salmon_fillets.jpg"));

            meals.add(new Meal(
                    "Steak with Veggies",
                    "Стек с зеленчуци",
                    600,
                    40,
                    "Steak, olive oil, garlic, asparagus, bell peppers, salt, pepper.",
                    "Стек, зехтин, чесън, аспержи, чушки, сол, черен пипер.",
                    "Season steak with salt, pepper, and minced garlic. Heat olive oil in a pan over medium-high heat and cook the steak for 4-5 minutes on each side for medium-rare. In the same pan, sauté asparagus and bell peppers until tender. Serve steak with veggies.",
                    "Подправете стека със сол, черен пипер и нарязан чесън. Загрейте зехтина в тиган на средно висока температура и гответе стека 4-5 минути от всяка страна за средно изпечено месо. В същия тиган задушете аспержите и чушките, докато омекнат. Сервирайте стека със зеленчуците.",
                    "/images/steak_veggies.jpg"));

            meals.add(new Meal(
                    "Egg White Omelette",
                    "Омлет с белтъци",
                    200,
                    20,
                    "Egg whites, spinach, mushrooms, tomatoes, olive oil, salt, pepper.",
                    "Белтъци, спанак, гъби, домати, зехтин, сол, черен пипер.",
                    "In a bowl, whisk egg whites with salt and pepper. Heat olive oil in a non-stick pan, add chopped spinach, sliced mushrooms, and diced tomatoes. Pour in the egg whites and cook until set. Fold the omelette and serve hot.",
                    "В купа разбийте белтъците със сол и черен пипер. Загрейте зехтина в незалепващ тиган, добавете нарязания спанак, нарязаните гъби и нарязаните на кубчета домати. Изсипете белтъците и гответе, докато се стегнат. Сгънете омлета и сервирайте топло.",
                    "/images/egg_white_omlette.jpg"));

            meals.add(new Meal(
                    "Greek Yogurt Parfait",
                    "Гръцки йогурт парфе",
                    250,
                    15,
                    "Greek yogurt, honey, granola, mixed berries.",
                    "Гръцки йогурт, мед, гранола, смесени плодове.",
                    "In a glass, layer Greek yogurt, a drizzle of honey, granola, and mixed berries. Repeat layers and serve immediately.",
                    "В чаша наредете слоеве от гръцки йогурт, капка мед, гранола и смесени плодове. Повторете слоевете и сервирайте веднага.",
                    "/images/greek_yogurt_parfait.jpg"));

            meals.add(new Meal(
                    "Shrimp Stir Fry",
                    "Скариди със зеленчуци",
                    400,
                    30,
                    "Shrimp, bell peppers, broccoli, soy sauce, garlic, ginger, olive oil.",
                    "Скариди, чушки, броколи, соев сос, чесън, джинджифил, зехтин.",
                    "Heat olive oil in a pan, add minced garlic and ginger, and sauté for 1 minute. Add shrimp and cook until pink. Remove shrimp and add chopped bell peppers and broccoli, stir-fry until tender. Return shrimp to the pan, add soy sauce, and cook for another 2 minutes. Serve hot.",
                    "Загрейте зехтина в тиган, добавете нарязания чесън и джинджифил и запържете за 1 минута. Добавете скаридите и гответе, докато станат розови. Извадете скаридите и добавете нарязаните чушки и броколи, запържете до омекване. Върнете скаридите в тигана, добавете соевия сос и гответе още 2 минути. Сервирайте горещо.",
                    "/images/shrimp_stir_fry.jpg"));

            meals.add(new Meal(
                    "Tuna Salad",
                    "Салата с риба тон",
                    300,
                    25,
                    "Canned tuna, Greek yogurt, celery, red onion, lemon juice, salt, pepper.",
                    "Консервирана риба тон, гръцки йогурт, целина, червен лук, лимонов сок, сол, черен пипер.",
                    "In a bowl, mix drained canned tuna with Greek yogurt, finely chopped celery, diced red onion, and a squeeze of lemon juice. Season with salt and pepper. Serve on whole grain bread or with lettuce wraps.",
                    "В купа смесете отцедената консервирана риба тон с гръцки йогурт, ситно нарязана целина, нарязан на кубчета червен лук и изцеден лимонов сок. Подправете със сол и черен пипер. Сервирайте върху пълнозърнест хляб или с листа от маруля.",
                    "/images/tuna_salad.jpg"));

            meals.add(new Meal(
                    "Cottage Cheese with Fruit",
                    "Кашкавал с плодове",
                    200,
                    15,
                    "Cottage cheese, mixed fruit (berries, pineapple, etc.), honey.",
                    "Кашкавал, смесени плодове (плодове, ананас и др.), мед.",
                    "In a bowl, combine cottage cheese with mixed fruit. Drizzle with honey and serve immediately.",
                    "В купа смесете кашкавала със смесени плодове. Полейте с мед и сервирайте веднага.",
                    "/images/cottage_cheese_fruit.jpg"));

            mealRepository.saveAll(meals);
        }
    }
//    private void initializeRoles() {
//        if (roleRepository.count() == 0) {
//            Role adminRole = new Role();
//            adminRole.setRoleType(RoleType.ADMIN);
//            roleRepository.save(adminRole);
//
//            Role userRole = new Role();
//            userRole.setRoleType(RoleType.USER);
//            roleRepository.save(userRole);
//        }
//    }
//
//    @PostConstruct
//    private void initializeAdminUser() {
//        Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN);
//        if (adminRole == null) {
//            throw new RuntimeException("Admin role is not initialized. Please initialize roles first.");
//        }
//
//        Optional<User> existingUser = userRepository.findByEmail("softuni@gmail.com");
//        if (existingUser.isEmpty()) {
//            User adminUser = new User();
//            adminUser.setUsername("softuni");
//            adminUser.setPassword(passwordEncoder.encode("softuni"));
//            adminUser.setFullName("Softuni Admin");
//            adminUser.setEmail("softuni@gmail.com");
//
//            // Assign admin role to the user
//            adminUser.setRoles(Set.of(adminRole));
//
//            userRepository.save(adminUser);
//        }
//    }
    @Override
    public void run(String... args) throws Exception {
        boolean programExists = this.programRepository.count() > 0;
        if (programExists) {
            return;
        } else {
            this.programRepository.save(new Program("Weight Loss", "Select this if you want to lose weight", ProgramGoal.LOSE_WEIGHT, 8));
            this.programRepository.save(new Program("Run More", "Select this if you want to run more", ProgramGoal.RUN_MORE, 8));
        }
        initMeals();

//        if (this.roleRepository.count() == 0) {
//            Role adminRole = new Role();
//            adminRole.setRoleType(RoleType.ADMIN);
//            roleRepository.save(adminRole);
//
//            Role userRole = new Role();
//            userRole.setRoleType(RoleType.USER);
//            roleRepository.save(userRole);
//        }
//
//        Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN);
//        if (adminRole == null) {
//            throw new RuntimeException("Admin role is not initialized. Please initialize roles first.");
//        }
//
//        Optional<User> existingUser = userRepository.findByEmail("softuni@gmail.com");
//        if (existingUser.isEmpty()) {
//            User adminUser = new User();
//            adminUser.setUsername("softuni");
//            adminUser.setPassword(passwordEncoder.encode("softuni"));
//            adminUser.setFullName("Softuni Admin");
//            adminUser.setEmail("softuni@gmail.com");
//            adminUser.setRoles(Set.of(adminRole));
//
//            userRepository.save(adminUser);
//        }
    }

}
