package local.vda.votingsystem.service;

import local.vda.votingsystem.DishTestData;
import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.repository.JpaUtil;
import local.vda.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static local.vda.votingsystem.DishTestData.*;
import static local.vda.votingsystem.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractDishServiceTest extends AbstractServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected DishService service;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() throws Exception {
        Dish newDish = DishTestData.getCreated();
        Dish created = service.create(new Dish(newDish), newDish.getRestaurant().getId());
        newDish.setId(created.getId());
        assertMatch(newDish, created);
    }

    @Test
    void delete() throws Exception {
        service.delete(DISH1_ID, DISH1.getRestaurant().getId());
        assertThrows(NotFoundException.class, () ->
                service.get(DISH1_ID, DISH1.getRestaurant().getId()));
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, 1));
    }

    @Test
    void get() throws Exception {
        Dish dish = service.get(DISH1_ID, RESTAURANT_1.getId());
        assertMatch(dish, DISH1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1, 1));
    }

    @Test
    void getByDate() throws Exception {
        List<Dish> dishes = service.getByDate(RESTAURANT_1_ID, DISH1.getDate());
        assertMatch(dishes, DISH1, DISH2);
    }

    @Test
    void getByDateNotFound() throws Exception {
        List<Dish> dishes = service.getByDate(1, DISH1.getDate());
        assertEquals(0, dishes.size());
    }

    @Test
    void update() throws Exception {
        Dish updated = DishTestData.getUpdated();
        service.update(updated, updated.getRestaurant().getId());
        assertMatch(service.get(updated.getId(), updated.getRestaurant().getId()), updated);
    }

    @Test
    void getBetween() {
        List<Dish> dishes = service.getBetween(DATE_1, DATE_1, RESTAURANT_1_ID);
        assertMatch(dishes, DISH1, DISH2);
    }

    @Test
    void getBetweenNotFound() {
        List<Dish> dishes = service.getBetween(LocalDate.now(), LocalDate.now(), RESTAURANT_1_ID);
        assertEquals(0, dishes.size());
    }


    @Test
    void testValidation() throws Exception {
        validateRootCause(() -> service.create(new Dish(null, " ", 1, DATE_1, RESTAURANT_1), RESTAURANT_1_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Dish(null, "name", -1, DATE_1, RESTAURANT_1), RESTAURANT_1_ID), ConstraintViolationException.class);
    }

    @Test
    void testGetByDateAndNameAndRestaurant() throws Exception {
        Dish result = service.getByDateAndName(DISH1.getDate(), DISH1.getName());
        assertMatch(result, DISH1);
    }
}