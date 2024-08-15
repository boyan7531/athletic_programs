//package bg.softuni.athleticprogramapplication.service.impl;
//
//import bg.softuni.athleticprogramapplication.entities.Meal;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//public class FavoriteMealClient {
//
//    private final RestTemplate restTemplate;
//    private final String sideProjectBaseUrl = "http://localhost:8081/api/users";
//
//    public FavoriteMealClient(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//    // GET: Retrieve favorite meals for a user from the side project
//    public List<Meal> getFavoriteMeals(Long userId) {
//        Meal[] meals = restTemplate.getForObject(sideProjectBaseUrl + "/" + userId + "/favorites", Meal[].class);
//        return Arrays.asList(meals);
//    }
//
//
//    public Meal addFavoriteMeal(Long userId, Meal meal) {
//        return restTemplate.postForObject(sideProjectBaseUrl + "/" + userId + "/favorites", meal, Meal.class);
//    }
//
//
//    public void deleteFavoriteMeal(Long userId, Long mealId) {
//        restTemplate.delete(sideProjectBaseUrl + "/" + userId + "/favorites/" + mealId);
//    }
//
//
//    public Meal updateFavoriteMeal(Long userId, Long mealId, Meal mealDetails) {
//        restTemplate.put(sideProjectBaseUrl + "/" + userId + "/favorites/" + mealId, mealDetails);
//        return mealDetails;
//    }
//}
