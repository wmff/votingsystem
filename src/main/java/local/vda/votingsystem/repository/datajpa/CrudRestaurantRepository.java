package local.vda.votingsystem.repository.datajpa;

import local.vda.votingsystem.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> getByName(String name);

    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:id AND d.date=:date")
    Restaurant getWithDishesByDate(@Param("id") int id, @Param("date") LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE d.date=:date ORDER BY r.name ASC")
    List<Restaurant> getAllWithDishesByDate(@Param("date") LocalDate date);

    @Override
    List<Restaurant> findAll(Sort sort);
}