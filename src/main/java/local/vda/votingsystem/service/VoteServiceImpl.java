package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.repository.RestaurantRepository;
import local.vda.votingsystem.repository.VoteRepository;
import local.vda.votingsystem.util.exception.VoteTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import static local.vda.votingsystem.util.DateTimeUtil.TIME_END_VOTING;
import static local.vda.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Vote createOrUpdate(int restaurantId, int userId) {
        checkNotFoundWithId(restaurantRepository.get(restaurantId), restaurantId);
        Vote vote = get(userId, LocalDate.now());
        if (!vote.isNew() && LocalTime.now().isAfter(TIME_END_VOTING)) {
            throw new VoteTimeException();
        }
        return voteRepository.save(vote, restaurantId, userId);
    }

    @Override
    public Vote get(int userId, LocalDate date) {
        return checkNotFoundWithId(voteRepository.get(userId, date), userId);
    }
}