package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.exceptions.ResourceNotFoundException;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.MealService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MealServiceImpl implements MealService {

    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;
    private final UserRepository userRepository;
//    private final RestTemplate restTemplate;

    public MealServiceImpl(ModelMapper modelMapper, MealRepository mealRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
//        this.restTemplate = restTemplate;
    }


    @Override
    public boolean create(MealAddBindingModel mealAddBindingModel) {
        Optional<Meal> byName = mealRepository.findByTitle(mealAddBindingModel.getName());
        if (byName.isPresent()) {
            return false;
        }
        Meal meal = this.modelMapper.map(mealAddBindingModel, Meal.class);
        mealRepository.save(meal);
        return true;
    }

    @Override
    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Meal> getFavoriteMeals(Long userId) {
        // Instead of calling an external service, get meals directly from the main project repository
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            User user = userById.get();
            return new HashSet<>(user.getFavoriteMeals());
        }
        return Set.of();
    }

    @Override
    public boolean removeFavoriteMeal(Long userId, Long mealId) {
        Optional<User> userById = userRepository.findById(userId);
        Optional<Meal> mealById = mealRepository.findById(mealId);
        if (userById.isPresent() && mealById.isPresent()) {
            User user = userById.get();
            Meal meal = mealById.get();
            user.getFavoriteMeals().remove(meal);
            userRepository.save(user);
        }
        return false;
    }

    @Override
    public List<Long> getFavoriteMealsIds(Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()) {
            User user = userById.get();
            List<Long> favoriteMealIds = new ArrayList<>();
            for (Meal meal : user.getFavoriteMeals()) {
                favoriteMealIds.add(meal.getId());
            }
            return favoriteMealIds;
        }
        return List.of();
    }

    @Override
    public boolean addFavoriteMeal(Long userId, Long mealId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new ResourceNotFoundException("Meal not found"));

        boolean isAdded = user.getFavoriteMeals().add(meal);
        if (isAdded) {
            userRepository.save(user);
        }

        return isAdded;


    }
}
