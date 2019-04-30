package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.repository.JpaUtil;
import local.vda.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static local.vda.votingsystem.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {
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
        Restaurant updated = getUpdated();
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
}