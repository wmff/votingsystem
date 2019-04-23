package local.vda.votingsystem;

import local.vda.votingsystem.model.Dish;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.LocalDate.of;
import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1;
import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_2;
import static local.vda.votingsystem.TestUtil.readListFromJsonMvcResult;
import static local.vda.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final int DISH1_ID = START_SEQ + 4;


    public static final LocalDate DATE_1 = of(2019, Month.MARCH, 21);
    public static final LocalDate DATE_2 = of(2019, Month.MARCH, 22);

    public static final Dish DISH1 = new Dish(DISH1_ID, "dish 1", 25900, DATE_1, RESTAURANT_1);
    public static final Dish DISH2 = new Dish(DISH1_ID + 1, "dish 2", 26900, DATE_1, RESTAURANT_1);
    public static final Dish DISH3 = new Dish(DISH1_ID + 2, "dish 3", 23900, DATE_1, RESTAURANT_2);
    public static final Dish DISH4 = new Dish(DISH1_ID + 3, "dish 4", 14900, DATE_1, RESTAURANT_2);
    public static final Dish DISH5 = new Dish(DISH1_ID + 4, "dish 5", 25900, DATE_2, RESTAURANT_1);
    public static final Dish DISH6 = new Dish(DISH1_ID + 5, "dish 6", 26900, DATE_2, RESTAURANT_1);
    public static final Dish DISH7 = new Dish(DISH1_ID + 6, "dish 7", 23900, DATE_2, RESTAURANT_2);
    public static final Dish DISH8 = new Dish(DISH1_ID + 7, "dish 8", 14900, DATE_2, RESTAURANT_2);

    public static final List<Dish> DISHES = List.of(DISH8, DISH7, DISH6, DISH6, DISH5, DISH4, DISH3, DISH2, DISH1);


    public static Dish getCreated() {
        return new Dish(null, "created dish", 100, of(2015, Month.JUNE, 1), RESTAURANT_1);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "updated dish 1", 300, of(2019, Month.MARCH, 21), RESTAURANT_1);
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Dish... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Dish> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Dish.class)).isEqualTo(expected);
    }
}
