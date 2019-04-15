package local.vda.votingsystem.repository;


import local.vda.votingsystem.model.Vote;

import java.time.LocalDate;

public interface VoteRepository {
    Vote save(Vote vote, int restaurantId, int userId);

    Vote get(int userId, LocalDate date);
}
