package local.vda.votingsystem.repository;


import local.vda.votingsystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface VoteRepository {
    Vote save(Vote vote, int restaurantId, int userId);

    Vote get(int userId, LocalDateTime dateTime);
}
