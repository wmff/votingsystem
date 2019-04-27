package local.vda.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.StringJoiner;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time", "restaurant_id"}, name = "votes_unique_date_time_restaurant_id_idx")})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date_time", nullable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    public Vote() {
    }

    public Vote(Integer id) {
        super(id);
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vote.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("date=" + date)
//                .add("user=" + user)
//                .add("restaurant=" + restaurant)
                .toString();
    }
}
