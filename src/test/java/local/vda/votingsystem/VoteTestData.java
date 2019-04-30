package local.vda.votingsystem;

import local.vda.votingsystem.model.Vote;

import java.util.List;

import static local.vda.votingsystem.RestaurantTestData.RESTAURANT_1;
import static local.vda.votingsystem.UserTestData.ADMIN;
import static local.vda.votingsystem.UserTestData.USER;
import static local.vda.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {
    public static final Vote VOTE1 = new Vote(START_SEQ + 13, RESTAURANT_1, USER);
    public static final Vote VOTE2 = new Vote(START_SEQ + 17, RESTAURANT_1, ADMIN);

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}
