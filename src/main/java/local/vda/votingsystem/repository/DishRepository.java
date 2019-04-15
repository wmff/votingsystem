package local.vda.votingsystem.repository;


import local.vda.votingsystem.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    Dish save(Dish dish, int restaurantId);

    boolean delete(int id, int restaurantId);

    Dish get(int id, int restaurantId);

    List<Dish> getByDate(int restaurantId, LocalDate date);

    List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId);
}
