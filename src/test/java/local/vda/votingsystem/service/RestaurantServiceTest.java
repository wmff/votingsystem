package local.vda.votingsystem.service;

import local.vda.votingsystem.DishTestData;
import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static local.vda.votingsystem.DishTestData.*;
import static local.vda.votingsystem.RestaurantTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractRestaurantServiceTest {
    @Test
    void getWithDishesByDate() throws Exception {
        Restaurant restaurant = service.getWithDishesByDate(RESTAURANT_1_ID, DATE_1);
        assertMatch(restaurant, RESTAURANT_1);
        DishTestData.assertMatch(restaurant.getDishes(), DISH1, DISH2);
    }

    @Test
    void getWithDishesByDateNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.getWithDishesByDate(RESTAURANT_1_ID, LocalDate.of(2000, Month.APRIL, 1)));
    }

    @Test
    void getAllWithDishesByDate() throws Exception {
        List<Restaurant> restaurants = service.getAllWithDishesByDate(DATE_1);
        assertMatch(restaurants, RESTAURANTS);
        DishTestData.assertMatch(restaurants.get(0).getDishes(), DISH1, DISH2);
        DishTestData.assertMatch(restaurants.get(1).getDishes(), DISH3, DISH4);
    }

    @Test
    void getAllWithDishesToday() throws Exception {
        List<Restaurant> restaurants = service.getAllWithDishesByDate(LocalDate.now());
        assertMatch(restaurants, RESTAURANT_2);
    }

    @Test
    void getAllWithDishesByDateNotFound() throws Exception {
        assertMatch(service.getAllWithDishesByDate(LocalDate.of(2000, Month.APRIL, 1)), List.of());
    }
}