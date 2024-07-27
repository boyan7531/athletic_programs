package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.service.MealService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService {

    private final ModelMapper modelMapper;
    private final MealRepository mealRepository;

    public MealServiceImpl(ModelMapper modelMapper, MealRepository mealRepository) {
        this.modelMapper = modelMapper;
        this.mealRepository = mealRepository;
    }

    @Override
    public boolean create(MealAddBindingModel mealAddBindingModel) {
        Optional<Meal> byName = mealRepository.findByName(mealAddBindingModel.getName());
        if(byName.isPresent()) {
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
}
