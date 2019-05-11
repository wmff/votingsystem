package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static local.vda.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishRepository repository;

    @Override
    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    @Override
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Override
    public List<Dish> getBetween(LocalDate startDate, LocalDate endDate, int restaurnatId) {
        Assert.notNull(startDate, "startDate must not be null");
        Assert.notNull(endDate, "endDate must not be null");
        return repository.getBetween(startDate, endDate, restaurnatId);
    }

    @Override
    public List<Dish> getByDate(int restaurantId, LocalDate date) {
        return repository.getByDate(restaurantId, (date == null) ? LocalDate.now() : date);
    }

    @Override
    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @Override
    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return repository.save(dish, restaurantId);
    }

    @Override
    public Dish getByDateAndName(LocalDate date, String name) {
        return repository.getByDateAndName(date, name);
    }
}