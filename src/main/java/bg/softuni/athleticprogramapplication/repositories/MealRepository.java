package bg.softuni.athleticprogramapplication.repositories;

import bg.softuni.athleticprogramapplication.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByTitle(String name);

    @Query("SELECT m.id FROM Meal m JOIN m.users u WHERE u.id = :userId")
    List<Long> findFavoriteMealIdsByUserId(@Param("userId") Long userId);


}
