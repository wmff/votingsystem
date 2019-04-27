package local.vda.votingsystem.web.restaurant;

import local.vda.votingsystem.model.Restaurant;
import local.vda.votingsystem.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.time.LocalDate;
import java.util.List;

import static local.vda.votingsystem.util.ValidationUtil.assureIdConsistent;
import static local.vda.votingsystem.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    @Autowired
    private UniqueNameValidator nameValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameValidator);
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Restaurant getByName(String name) {
        log.info("getByName {}", name);
        return service.getByName(name);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Restaurant> getAllWithDishesToday() {
        log.info("getAllWithDishesToday");
        return service.getAllWithDishesByDate(LocalDate.now());
    }

    public Restaurant getAllWithDishesToday(int id) {
        log.info("getAllWithDishesToday by id={}", id);
        return service.getWithDishesByDate(id, LocalDate.now());
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }
}