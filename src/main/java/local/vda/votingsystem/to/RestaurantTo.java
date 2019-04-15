package local.vda.votingsystem.to;

import java.time.LocalDate;
import java.util.Objects;

public class RestaurantTo {
    private Integer id;

    private LocalDate date;

    private String name;

    private boolean selected;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, LocalDate date, String name, boolean selected) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.selected = selected;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name +
                ", selected=" + selected+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return selected == that.selected &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, name, selected);
    }
}