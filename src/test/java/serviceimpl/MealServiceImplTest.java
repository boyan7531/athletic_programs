package serviceimpl;

import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.repositories.MealRepository;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.impl.MealServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceImplTest {

    @InjectMocks
    private MealServiceImpl mealService;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private Meal testMeal;
    private User testUser;

    @BeforeEach
    void setUp() {
        testMeal = new Meal();
        testMeal.setId(1L);
        testMeal.setTitle("Test Meal");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setFavoriteMeals(new HashSet<>());
    }

    @Test
    void testGetAllMeals() {
        when(mealRepository.findAll()).thenReturn(Arrays.asList(testMeal));

        assertEquals(1, mealService.getAllMeals().size());
        assertEquals("Test Meal", mealService.getAllMeals().get(0).getTitle());

        verify(mealRepository, times(1)).findAll();
    }

    @Test
    void testGetMealById() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));

        Meal meal = mealService.getMealById(1L);
        assertNotNull(meal);
        assertEquals("Test Meal", meal.getTitle());

        verify(mealRepository, times(1)).findById(1L);
    }

    @Test
    void testAddToFavorite() {
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

//        boolean result = mealService.addFavoriteMeal(1L, );
//        assertTrue(result);
//        assertTrue(testUser.getFavoriteMeals().contains(testMeal));
//        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testCreateMeal() {
        MealAddBindingModel mealAddBindingModel = new MealAddBindingModel();
        mealAddBindingModel.setName("New Meal");

        when(mealRepository.findByTitle(mealAddBindingModel.getName())).thenReturn(Optional.empty());
        when(modelMapper.map(mealAddBindingModel, Meal.class)).thenReturn(testMeal);

        boolean result = mealService.create(mealAddBindingModel);
        assertTrue(result);
        verify(mealRepository, times(1)).save(testMeal);
    }

    @Test
    void testGetFavoriteMeals() {
        testUser.getFavoriteMeals().add(testMeal);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

//        assertEquals(1, mealService.getFavoriteMeals(1L).size());
//        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveFromFavorite() {
        testUser.getFavoriteMeals().add(testMeal);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));

//        mealService.removeFromFavorite(1L, 1L);
//        assertEquals(0, testUser.getFavoriteMeals().size());
//        verify(userRepository, times(1)).save(testUser);
    }
}
