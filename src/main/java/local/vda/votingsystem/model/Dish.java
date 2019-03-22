package local.vda.votingsystem.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Dish.ALL_SORTED, query = "SELECT d FROM Dishes d WHERE d.user.id=:userId ORDER BY d.date, d.restaurant_id"),
        @NamedQuery(name = Dish.GET_BY_DATE, query = "SELECT d FROM Dishes d " +
                "WHERE d.restaurant.id=:restaurantId AND d.date = :date ORDER BY d.date DESC"),
})
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "restaurant_id"}, name = "dishes_unique_restaurant_id_date_idx")})
public class Dish extends AbstractNamedEntity {
    public static final String ALL_SORTED = "Dish.getAll";
    public static final String GET_BY_DATE = "Dish.getByDate";

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "price", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private int price;

    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(String name, int price, LocalDate date) {
        this(null, name, price, date);
    }

    public Dish(Integer id, String name, int price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                ", date='" + date +
                '}';
    }
}
