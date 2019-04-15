package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Vote;

import java.time.LocalDate;

public interface VoteService {
    Vote set(int restaurantId, int userId);

    Vote get(int userId, LocalDate date);
}