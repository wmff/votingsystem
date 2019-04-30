package local.vda.votingsystem.repository.datajpa;

import local.vda.votingsystem.model.Vote;
import local.vda.votingsystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {
    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Vote save(Vote vote, int restaurantId, int userId) {
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        vote.setUser(crudUserRepository.getOne(userId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public Vote get(int userId, LocalDate date) {
        return crudVoteRepository.findByUserIdAndDate(userId, date).orElse(new Vote());
    }
}
