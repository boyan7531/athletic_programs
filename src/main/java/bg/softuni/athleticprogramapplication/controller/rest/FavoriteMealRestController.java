//package bg.softuni.athleticprogramapplication.controller.rest;
//
//import bg.softuni.athleticprogramapplication.entities.Meal;
//import bg.softuni.athleticprogramapplication.service.MealService;
//import bg.softuni.athleticprogramapplication.service.impl.FavoriteMealClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Set;
//
//@RestController
//@RequestMapping("/api/users")
//public class FavoriteMealRestController {
//
////    private final MealService mealService;
////
////    @Autowired
////    public FavoriteMealRestController(MealService mealService) {
////        this.mealService = mealService;
////    }
////
////    @GetMapping("/{userId}/favorites")
////    public ResponseEntity<Set<Meal>> getFavoriteMeals(@PathVariable Long userId) {
////        Set<Meal> favoriteMeals = mealService.getFavoriteMeals(userId);
////        if (favoriteMeals == null || favoriteMeals.isEmpty()) {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////        return new ResponseEntity<>(favoriteMeals, HttpStatus.OK);
////    }
//
//
//    private final MealService mealService;
//
//    @Autowired
//    public FavoriteMealRestController(MealService mealService) {
//        this.mealService = mealService;
//    }
//
//    @GetMapping("/{userId}/favorites")
//    public ResponseEntity<Set<Meal>> getFavoriteMeals(@PathVariable Long userId) {
//        Set<Meal> favoriteMeals = mealService.getFavoriteMeals(userId);
//        if (favoriteMeals == null || favoriteMeals.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return ResponseEntity.ok(favoriteMeals);
//    }
//
//    @PostMapping("/{userId}/favorites")
//    public ResponseEntity<Meal> addFavoriteMeal(@PathVariable Long userId, @RequestBody Meal meal) {
//        Meal newFavoriteMeal = mealService.addToFavorite(meal.getId(), userId);
//        if (newFavoriteMeal != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(newFavoriteMeal);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
//
//    @DeleteMapping("/{userId}/favorites/{mealId}")
//    public ResponseEntity<Void> deleteFavoriteMeal(@PathVariable Long userId, @PathVariable Long mealId) {
//        boolean success = mealService.removeFromFavorite(userId, mealId);
//        if (success) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    @PutMapping("/{userId}/favorites/{mealId}")
//    public ResponseEntity<Meal> updateFavoriteMeal(@PathVariable Long userId, @PathVariable Long mealId, @RequestBody Meal mealDetails) {
//        Meal updatedMeal = mealService.updateMeal(mealId, mealDetails);
//        if (updatedMeal != null) {
//            return ResponseEntity.ok(updatedMeal);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//}
