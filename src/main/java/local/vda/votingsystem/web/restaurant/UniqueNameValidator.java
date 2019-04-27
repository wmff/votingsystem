package local.vda.votingsystem.web.restaurant;


import local.vda.votingsystem.HasName;
import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.repository.RestaurantRepository;
import local.vda.votingsystem.web.ExceptionInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UniqueNameValidator implements org.springframework.validation.Validator {

    @Autowired
    private RestaurantRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasName.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasName restaurant = ((HasName) target);
        Restaurant dbRestaurant = repository.getByName(restaurant.getName().toLowerCase());
        if (dbRestaurant != null && !dbRestaurant.getId().equals(restaurant.getId())) {
            errors.rejectValue("name", ExceptionInfoHandler.EXCEPTION_RESTAURANT_DUPLICATE_NAME);
        }
    }
}
