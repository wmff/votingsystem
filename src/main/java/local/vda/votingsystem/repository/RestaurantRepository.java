package local.vda.votingsystem.repository;


import local.vda.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    Restaurant getByName(String name);

    Restaurant getWithDishesByDate(int id, LocalDate date);

    List<Restaurant> getAllWithDishesByDate(LocalDate date);

    List<Restaurant> getAll();
}
