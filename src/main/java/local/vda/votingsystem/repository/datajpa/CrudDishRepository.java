package local.vda.votingsystem.repository.datajpa;

import local.vda.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaturantId);

    @Override
    @Transactional
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId AND d.date=:date ORDER BY d.date DESC")
    List<Dish> getByDate(@Param("restaurantId") int restaurantId, @Param("date") LocalDate date);

    @Query("SELECT d from Dish d WHERE d.restaurant.id=:restaurantId AND d.date BETWEEN :startDate AND :endDate ORDER BY d.date DESC")
    List<Dish> getBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("restaurantId") int restaurantId);

    @Query("SELECT d from Dish d WHERE d.date=:date AND d.name=:name")
    Dish getByDateName(@Param("date") LocalDate date, @Param("name") String name);
}