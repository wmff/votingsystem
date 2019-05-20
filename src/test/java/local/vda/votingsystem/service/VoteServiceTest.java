package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.repository.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;

import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static local.vda.votingsystem.UserTestData.USER_ID;
import static local.vda.votingsystem.util.DateTimeUtil.TIME_END_VOTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VoteServiceTest extends AbstractServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected VoteService service;

    @BeforeEach
    void setUp() {
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void testCreateOrUpdate() {
        TIME_END_VOTING = LocalTime.MAX;
        Vote created = service.createOrUpdate(RESTAURANT_1_ID, USER_ID);
        assertEquals(RESTAURANT_1_ID, created.getRestaurant().getId());
        assertEquals(USER_ID, created.getUser().getId());
        assertEquals(LocalDate.now(), created.getDate());
    }
}