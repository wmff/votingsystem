package local.vda.votingsystem.web.restaurant;

import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.service.RestaurantService;
import local.vda.votingsystem.util.exception.ErrorType;
import local.vda.votingsystem.web.AbstractControllerTest;
import local.vda.votingsystem.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static local.vda.votingsystem.RestaurantTestData.*;
import static local.vda.votingsystem.TestUtil.readFromJson;
import static local.vda.votingsystem.TestUtil.userHttpBasic;
import static local.vda.votingsystem.UserTestData.ADMIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantAdminRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantAdminRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANTS));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
    }

    @Test
    void testGetWithDishes() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_2.getId() + "/withDishes")
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_2));
    }

    @Test
    void testCreate() throws Exception {
        Restaurant created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(ADMIN)));

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());

        assertMatch(returned, created);
        assertMatch(service.getAll(), RESTAURANT_1, RESTAURANT_2, created);
    }

    @Test
    void testCreateWithIllegalRequestData() throws Exception {
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(RESTAURANT_1)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    void testCreateInvalid() throws Exception {
        Restaurant invalid = new Restaurant("");
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testCreateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_1.getName());
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(), RESTAURANT_2);
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
        Restaurant updated = getUpdated();

        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());

        assertMatch(service.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void testUpdateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_1_ID, "");
        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andDo(print());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testUpdateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT_1_ID, RESTAURANT_2.getName());

        mockMvc.perform(put(REST_URL + RESTAURANT_1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR));
    }
}
