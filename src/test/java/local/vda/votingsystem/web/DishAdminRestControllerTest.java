package local.vda.votingsystem.web;

import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.service.DishService;
import local.vda.votingsystem.util.exception.ErrorType;
import local.vda.votingsystem.web.json.JsonUtil;
import local.vda.votingsystem.web.restaurant.RestaurantAdminRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static local.vda.votingsystem.DishTestData.*;
import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1;
import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static local.vda.votingsystem.TestUtil.readFromJson;
import static local.vda.votingsystem.TestUtil.userHttpBasic;
import static local.vda.votingsystem.UserTestData.ADMIN;
import static local.vda.votingsystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DishAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/' + RESTAURANT_1_ID + "/dishes/";

    @Autowired
    private DishService service;

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DISH1));
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "1")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + DISH1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(service.getByDate(RESTAURANT_1_ID, LocalDate.now()), List.of());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + "1")
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void testUpdate() throws Exception {
        Dish updated = getUpdated();

        mockMvc.perform(put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(DISH1_ID, RESTAURANT_1_ID), updated);
    }

    @Test
    void testCreate() throws Exception {
        Dish created = new Dish(null, "new dish", 111, LocalDate.now(), RESTAURANT_1);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Dish returned = readFromJson(action, Dish.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getByDate(RESTAURANT_1_ID, LocalDate.now()), created);
    }

    @Test
    void testCreateWithIllegalRequestData() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(DISH1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + "/")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        Dish dish = new Dish(null, DISH1.getName(), 111, DISH1.getDate(), RESTAURANT_1);

        mockMvc.perform(put(REST_URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicate() throws Exception {
        Dish dish = new Dish(null, DISH2.getName(), 111, DISH2.getDate(), RESTAURANT_1);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }

}
