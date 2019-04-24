package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.repository.JpaUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1_ID;
import static local.vda.votingsystem.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractVoteServiceTest extends AbstractServiceTest {
    @Autowired
    private JpaUtil jpaUtil;

    @Autowired
    protected VoteService service;

    @BeforeEach
    void setUp() throws Exception {
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void set() throws Exception {
        Vote created = service.set(RESTAURANT_1_ID, USER_ID);
        assertEquals(RESTAURANT_1_ID, created.getRestaurant().getId());
        assertEquals(USER_ID, created.getUser().getId());
        assertEquals(LocalDate.now(), created.getDate());
    }
}