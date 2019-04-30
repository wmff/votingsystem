package local.vda.votingsystem.web;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.util.exception.ErrorType;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;

import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static local.vda.votingsystem.TestUtil.readFromJson;
import static local.vda.votingsystem.TestUtil.userHttpBasic;
import static local.vda.votingsystem.UserTestData.ADMIN;
import static local.vda.votingsystem.UserTestData.USER;
import static local.vda.votingsystem.VoteTestData.*;
import static local.vda.votingsystem.util.DateTimeUtil.TIME_END_VOTING;
import static local.vda.votingsystem.util.exception.ErrorType.DATA_NOT_FOUND;
import static local.vda.votingsystem.util.exception.VoteTimeException.EXCEPTION_TIME_END_VOTING;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Test
    void testVoteUnAuth() throws Exception {
        mockMvc.perform(post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testVoteBeforeTimeEnd() throws Exception {
        TIME_END_VOTING = LocalTime.MAX;
        ResultActions actions = mockMvc.perform(get(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote returned = readFromJson(actions, Vote.class);
        assertMatch(returned, VOTE1);
    }

    @Test
    void testVoteAfterTimeEnd() throws Exception {
        TIME_END_VOTING = LocalTime.MIN;
        ResultActions actions = mockMvc.perform(get(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote returned = readFromJson(actions, Vote.class);
        assertMatch(returned, VOTE2);
    }

    @Test
    void testReVoteAfterTimeEnd() throws Exception {
        TIME_END_VOTING = LocalTime.MIN;
        ResultActions action = mockMvc.perform(get(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(USER)))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(errorType(ErrorType.APP_ERROR))
                .andExpect(detailMessage(EXCEPTION_TIME_END_VOTING));
    }

    @Test
    void testReVoteBeforeTimeEnd() throws Exception {
        TIME_END_VOTING = LocalTime.MAX;
        ResultActions actions = mockMvc.perform(get(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());

        Vote returned = readFromJson(actions, Vote.class);
        assertMatch(returned, VOTE1);
    }

    @Test
    void testBadVote() throws Exception {
        mockMvc.perform(get(REST_URL)
                .param("restaurantId", "1")
                .with(userHttpBasic(USER)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }
}
