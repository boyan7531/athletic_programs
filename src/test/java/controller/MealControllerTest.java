package controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.controller.MealController;
import bg.softuni.athleticprogramapplication.entities.Meal;
import bg.softuni.athleticprogramapplication.entities.dto.binding.MealAddBindingModel;
import bg.softuni.athleticprogramapplication.service.MealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MealControllerTest {

    @Mock
    private MealService mealService;

    @Mock
    private UserSession userSession;

    @InjectMocks
    private MealController mealController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mealController).build();
    }

//    @Test
//    public void testAddMeal_GET_ShouldReturnAddMealView() throws Exception {
//        mockMvc.perform(get("/add-meal"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add-meal"));
//    }

//    @Test
//    public void testDoAddMeal_POST_ShouldRedirectWhenBindingResultHasErrors() throws Exception {
//        BindingResult bindingResult = mock(BindingResult.class);
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        mockMvc.perform(post("/add-meal")
//                        .flashAttr("mealAddBindingModel", new MealAddBindingModel())
//                        .flashAttr("org.springframework.validation.BindingResult.mealAddBindingModel", bindingResult))
//                .andExpect(redirectedUrl("/add-meal"));
//    }
//
//    @Test
//    public void testDoAddMeal_POST_ShouldRedirectWhenMealServiceFails() throws Exception {
//        when(mealService.create(any(MealAddBindingModel.class))).thenReturn(false);
//
//        mockMvc.perform(post("/add-meal")
//                        .flashAttr("mealAddBindingModel", new MealAddBindingModel()))
//                .andExpect(redirectedUrl("/add-meal"));
//    }

    @Test
    void testGetAllMeals_GET_ShouldReturnMealsView() throws Exception {
        mockMvc.perform(get("/meals"))
                .andExpect(status().isOk())
                .andExpect(view().name("mealsList"))
                .andExpect(model().attributeExists("meals"));
    }


    @Test
    public void testGetMealInfo_GET_ShouldReturnMealDetailsView() throws Exception {
        when(mealService.getMealById(anyLong())).thenReturn(new Meal());

        mockMvc.perform(get("/meals/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("meal-details"));
    }

    @Test
    public void testAddToFavorite_POST_ShouldRedirectToLoginWhenNotLoggedIn() throws Exception {
        when(userSession.isLoggedIn()).thenReturn(false);

        mockMvc.perform(post("/meals/{mealId}/favorite", 1L))
                .andExpect(redirectedUrl("/users/login"));
    }

    @Test
    public void testAddToFavorite_POST_ShouldRedirectToMealsWhenLoggedIn() throws Exception {
        when(userSession.isLoggedIn()).thenReturn(true);
        when(userSession.getId()).thenReturn(1L);
        when(mealService.addFavoriteMeal(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/meals/{mealId}/favorite", 1L))
                .andExpect(redirectedUrl("/meals"));
    }

    @Test
    public void testGetFavoriteMeals_GET_ShouldReturnFavoriteMealsView() throws Exception {
        when(userSession.getId()).thenReturn(1L);
        when(mealService.getFavoriteMeals(anyLong())).thenReturn(Set.of(new Meal()));

        mockMvc.perform(get("/users/favorites"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-favorite-meals"));
    }

    @Test
    public void testRemoveFavoriteMeal_POST_ShouldRedirectToFavorites() throws Exception {
        mockMvc.perform(post("/meals/unfavorite/{mealId}", 1L))
                .andExpect(redirectedUrl("/users/favorites"));
    }
}
