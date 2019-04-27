package local.vda.votingsystem;

import local.vda.votingsystem.model.Restaurant;

import java.time.LocalDate;

public interface HasDate extends HasId, HasName {
    LocalDate getDate();
    Restaurant getRestaurant();
}