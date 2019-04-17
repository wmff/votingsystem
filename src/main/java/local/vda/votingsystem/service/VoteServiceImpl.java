package local.vda.votingsystem.service;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.repository.RestaurantRepository;
import local.vda.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static local.vda.votingsystem.util.DateTimeUtil.TIME_END_VOTING;
import static local.vda.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public Vote set(int restaurantId, int userId) {
        checkNotFoundWithId(restaurantRepository.get(restaurantId), restaurantId);
        Vote vote = get(userId, LocalDateTime.now());
        if (!vote.isNew()) {
            if (LocalTime.now().isAfter(TIME_END_VOTING)) {
                throw new RuntimeException("time voting exprired");
            }
        }
        return voteRepository.save(vote, restaurantId, userId);
    }

    @Override
    public Vote get(int userId, LocalDateTime dateTime) {
        return checkNotFoundWithId(voteRepository.get(userId, dateTime), userId);
    }
}