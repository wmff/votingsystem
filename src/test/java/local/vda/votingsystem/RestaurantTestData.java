package local.vda.votingsystem;

import local.vda.votingsystem.model.Restaurant;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static local.vda.votingsystem.TestUtil.readListFromJsonMvcResult;
import static local.vda.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int RESTAURANT_1_ID = START_SEQ + 2;

    public static final String RESTAURANT_1_NAME = "restaurant 1";
    public static final String RESTAURANT_2_NAME = "restaurant 2";

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, RESTAURANT_1_NAME);
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_1_ID + 1, RESTAURANT_2_NAME);

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_2);

    public static Restaurant getCreated() {
        return new Restaurant(null, "Созданный ресторан");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_1_ID, "Обновленный ресторан");
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dishes", "votes");
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Restaurant... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<Restaurant> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Restaurant.class)).isEqualTo(expected);
    }

}
