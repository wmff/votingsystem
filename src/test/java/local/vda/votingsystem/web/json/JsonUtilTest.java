package local.vda.votingsystem.web.json;

import local.vda.votingsystem.UserTestData;
import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static local.vda.votingsystem.DishTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {
    @Test
    void testReadWriteValue() {
        String json = JsonUtil.writeValue(DISH1);
        System.out.println(json);
        Dish dish = JsonUtil.readValue(json, Dish.class);
        assertMatch(dish, DISH1);
    }

    @Test
    void testReadWriteValues() {
        String json = JsonUtil.writeValue(DISHES);
        System.out.println(json);
        List<Dish> meals = JsonUtil.readValues(json, Dish.class);
        assertMatch(meals, DISHES);
    }

    @Test
    void testWriteOnlyAccess() {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}