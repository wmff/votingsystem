package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface DishService {
    Dish get(int id, int restaurantId) throws NotFoundException;

    void delete(int id, int restaurantId) throws NotFoundException;

    List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId);

    List<Dish> getByDate(int restaurantId, LocalDate date);

    void update(Dish dish, int restaurantId) throws NotFoundException;

    Dish create(Dish dish, int restaurantId);

}