package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantService {
    Restaurant get(int id) throws NotFoundException;

    Restaurant getByName(String name) throws NotFoundException;

    void delete(int id) throws NotFoundException;

    void update(Restaurant restaurant) throws NotFoundException;

    Restaurant create(Restaurant restaurant);

    List<Restaurant> getWithMealsByDate(int id, LocalDate date);

    List<Restaurant> getWithMealsByDate(LocalDate date);

    List<Restaurant> getAll();
}