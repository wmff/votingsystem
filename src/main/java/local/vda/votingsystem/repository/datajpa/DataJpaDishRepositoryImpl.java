package local.vda.votingsystem.repository.datajpa;

import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepositoryImpl implements DishRepository {

    @Autowired
    private CrudDishRepository crudDishRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Override
    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudDishRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudDishRepository.delete(id, userId) != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return crudDishRepository.findById(id).filter(dish -> dish.getRestaurant().getId() == restaurantId).orElse(null);
    }

    @Override
    public List<Dish> getByDate(int restaurantId, LocalDate date) {
        return crudDishRepository.getByDate(restaurantId, date);
    }

    @Override
    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudDishRepository.getBetween(startDate, endDate, restaurantId);
    }

    @Override
    public Dish getByDateAndName(LocalDate date, String name) {
        return crudDishRepository.getByDateName(date, name);
    }
}
