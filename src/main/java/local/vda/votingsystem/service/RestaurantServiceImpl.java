package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.repository.RestaurantRepository;
import local.vda.votingsystem.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static local.vda.votingsystem.util.ValidationUtil.checkNotFound;
import static local.vda.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public Restaurant get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Restaurant getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.getByName(name), "name=" + name);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public void update(Restaurant restaurant) throws NotFoundException {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    @Override
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Override
    public List<Restaurant> getWithMealsByDate(int id, LocalDate date) {
        return checkNotFoundWithId(repository.getWithDishesByDate(id, date), id);
    }

    @Override
    public List<Restaurant> getWithMealsByDate(LocalDate date) {
        return repository.getWithDishesByDate(date);
    }

    @Override
    public List<Restaurant> getAll() {
        return repository.getAll();
    }
}