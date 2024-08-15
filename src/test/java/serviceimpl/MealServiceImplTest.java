package serviceimpl;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.exceptions.ResourceNotFoundException;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MealServiceImplTest {

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MealServiceImpl mealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnFalse_WhenMealAlreadyExists() {
        MealAddBindingModel mealAddBindingModel = new MealAddBindingModel();
        mealAddBindingModel.setName("Meal 1");

        when(mealRepository.findByTitle("Meal 1")).thenReturn(Optional.of(new Meal()));

        boolean result = mealService.create(mealAddBindingModel);

        assertFalse(result);
        verify(mealRepository, never()).save(any(Meal.class));
    }

    @Test
    void create_ShouldReturnTrue_WhenMealDoesNotExist() {
        MealAddBindingModel mealAddBindingModel = new MealAddBindingModel();
        mealAddBindingModel.setName("Meal 1");

        when(mealRepository.findByTitle("Meal 1")).thenReturn(Optional.empty());
        when(modelMapper.map(mealAddBindingModel, Meal.class)).thenReturn(new Meal());

        boolean result = mealService.create(mealAddBindingModel);

        assertTrue(result);
        verify(mealRepository, times(1)).save(any(Meal.class));
    }

    @Test
    void getAllMeals_ShouldReturnListOfMeals() {
        List<Meal> meals = List.of(new Meal(), new Meal());
        when(mealRepository.findAll()).thenReturn(meals);

        List<Meal> result = mealService.getAllMeals();

        assertEquals(2, result.size());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void getMealById_ShouldReturnMeal_WhenMealExists() {
        Meal meal = new Meal();
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        Meal result = mealService.getMealById(1L);

        assertEquals(meal, result);
    }

    @Test
    void getMealById_ShouldThrowException_WhenMealDoesNotExist() {
        when(mealRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.getMealById(1L));
    }

    @Test
    void getFavoriteMeals_ShouldReturnSetOfMeals_WhenUserExists() {
        User user = new User();
        Meal meal = new Meal();
        user.setFavoriteMeals(Set.of(meal));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Set<Meal> result = mealService.getFavoriteMeals(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getFavoriteMeals_ShouldReturnEmptySet_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Set<Meal> result = mealService.getFavoriteMeals(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void removeFavoriteMeal_ShouldRemoveMeal_WhenUserAndMealExist() {
        User user = new User();
        Meal meal = new Meal();
        user.setFavoriteMeals(Set.of(meal));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        boolean result = mealService.removeFavoriteMeal(1L, 1L);

        assertTrue(user.getFavoriteMeals().isEmpty());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void removeFavoriteMeal_ShouldReturnFalse_WhenUserOrMealDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = mealService.removeFavoriteMeal(1L, 1L);

        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getFavoriteMealsIds_ShouldReturnListOfIds_WhenUserExists() {
        User user = new User();
        Meal meal = new Meal();
        meal.setId(1L);
        user.setFavoriteMeals(Set.of(meal));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Long> result = mealService.getFavoriteMealsIds(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0));
    }

    @Test
    void getFavoriteMealsIds_ShouldReturnEmptyList_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        List<Long> result = mealService.getFavoriteMealsIds(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void addFavoriteMeal_ShouldAddMeal_WhenUserAndMealExist() {
        User user = new User();
        Meal meal = new Meal();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(meal));

        boolean result = mealService.addFavoriteMeal(1L, 1L);

        assertTrue(result);
        assertTrue(user.getFavoriteMeals().contains(meal));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void addFavoriteMeal_ShouldThrowException_WhenUserOrMealDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mealService.addFavoriteMeal(1L, 1L));
    }
}
