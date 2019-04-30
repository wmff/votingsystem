package local.vda.votingsystem.web;


import local.vda.votingsystem.HasDate;
import local.vda.votingsystem.HasName;
import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UniqueNameDishValidator implements org.springframework.validation.Validator {
    @Autowired
    private DishRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return HasName.class.isAssignableFrom(clazz) && HasDate.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        HasDate dish = ((HasDate) target);
        Dish dbDish = repository.getByDateAndName(dish.getDate(), dish.getName().toLowerCase());
        if (dbDish != null && !dbDish.getId().equals(dish.getId())) {
            errors.rejectValue("name", ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME);
        }
    }
}
