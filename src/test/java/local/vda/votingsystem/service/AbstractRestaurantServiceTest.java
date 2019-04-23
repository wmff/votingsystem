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
    void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "new restaurant");
        Restaurant created = service.create(new Restaurant(newRestaurant));
        newRestaurant.setId(created.getId());
        assertMatch(newRestaurant, created);
        assertMatch(service.getAll(), newRestaurant, RESTAURANT_1, RESTAURANT_2);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "restaurant 1")));
    }

    @Test
    void delete() throws Exception {
        service.delete(RESTAURANT_1_ID);
        assertMatch(service.getAll(), RESTAURANT_2);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() throws Exception {
        Restaurant restaurant = service.get(RESTAURANT_1_ID);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByName() throws Exception {
        Restaurant restaurant = service.getByName(RESTAURANT_1_NAME);
        assertMatch(restaurant, RESTAURANT_1);
    }

    @Test
    void getByNameNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.getByName("1"));
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        service.update(updated);
        assertMatch(service.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void getAll() throws Exception {
        List<Restaurant> all = service.getAll();
        assertMatch(all, RESTAURANTS);
    }

    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")), ConstraintViolationException.class);
    }
}