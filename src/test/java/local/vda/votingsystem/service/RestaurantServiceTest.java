package local.vda.votingsystem.service;

import local.vda.votingsystem.DishTestData;
import local.vda.votingsystem.RestaurantTestData;
import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.repository.JpaUtil;
import local.vda.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static local.vda.votingsystem.DishTestData.*;
import static local.vda.votingsystem.DishTestData.DISH4;
import static local.vda.votingsystem.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected RestaurantService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager.getCache("restaurants").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void testCreate() {
        Restaurant newRestaurant = new Restaurant(null, "new restaurant");
        Restaurant created = service.create(new Restaurant(newRestaurant));
        newRestaurant.setId(created.getId());
        assertMatch(newRestaurant, created);
        assertMatch(service.getAll(), newRestaurant, RESTAURANT_1, RESTAURANT_2);
    }

    @Test
    void testDuplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "restaurant 1")));
    }

    @Test
    void testDelete() {
        service.delete(RESTAURANT_1_ID);
        assertMatch(service.getAll(), RESTAURANT_2);
    }

    @Test
    void testDeletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void testGet() {
        Restaurant restaurant = service.get(RESTAURANT_1_ID);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void testGetNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void testGetByName() {
        Restaurant restaurant = service.getByName(RESTAURANT_1_NAME);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void testGetByNameNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.getByName("1"));
    }

    @Test
    void testUpdate() {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.update(updated);
        assertMatch(service.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void testGetAll() {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RESTAURANTS);
    }

    @Test
    void testValidation() {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")), ConstraintViolationException.class);
    }

    @Test
    void testGetWithDishesByDate() {
        Restaurant restaurant = service.getWithDishesByDate(RESTAURANT_1_ID, DATE_1);
        assertMatch(restaurant, RESTAURANT_1);
        DishTestData.assertMatch(restaurant.getDishes(), DISH1, DISH2);
    }

    @Test
    void testGetWithDishesByDateNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.getWithDishesByDate(RESTAURANT_1_ID, LocalDate.of(2000, Month.APRIL, 1)));
    }

    @Test
    void testGetAllWithDishesByDate() {
        List<Restaurant> restaurants = service.getAllWithDishesByDate(DATE_1);
        assertMatch(restaurants, RESTAURANTS);
        DishTestData.assertMatch(restaurants.get(0).getDishes(), DISH1, DISH2);
        DishTestData.assertMatch(restaurants.get(1).getDishes(), DISH3, DISH4);
    }

    @Test
    void testGetAllWithDishesToday() {
        List<Restaurant> restaurants = service.getAllWithDishesByDate(LocalDate.now());
        assertMatch(restaurants, RESTAURANT_2);
    }

    @Test
    void testGetAllWithDishesByDateNotFound() {
        assertMatch(service.getAllWithDishesByDate(LocalDate.of(2000, Month.APRIL, 1)), List.of());
    }
}