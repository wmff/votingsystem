package local.vda.votingsystem.web;

import local.vda.votingsystem.View;
import local.vda.votingsystem.model.Dish;
import local.vda.votingsystem.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static local.vda.votingsystem.util.ValidationUtil.assureIdConsistent;
import static local.vda.votingsystem.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishAdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishAdminRestController {
    static final String REST_URL = "/rest/admin/restaurants/{restaurantId}/dishes";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService service;

    @Autowired
    private UniqueNameDishValidator nameDishValidatorr;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(nameDishValidatorr);
    }

    @GetMapping
    public List<Dish> getAllByDate(@PathVariable int restaurantId, @RequestParam(value = "date", required = false) LocalDate date) {
        log.info("getAll");
        return service.getByDate(restaurantId, date);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllBetween(@PathVariable int restaurantId,
                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                    @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        log.info("getAllBetween");
        return service.getBetween(startDate, endDate, restaurantId);
    }


    @GetMapping(value = "/{dishId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("get {}", dishId);
        return service.get(dishId, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create {}", dish);
        checkNew(dish);
        Dish created = service.create(dish, restaurantId);
        Map<String, Integer> parameters = new HashMap<>();
        parameters.put("restaurantId", restaurantId);
        parameters.put("dishId", created.getId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{dishId}")
                .buildAndExpand(parameters).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restaurantId, @PathVariable int dishId) {
        log.info("delete {}", dishId);
        service.delete(dishId, restaurantId);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish, @PathVariable("restaurantId") int restaurantId, @PathVariable int dishId) {
        log.info("update {} with id={}", dish, dishId);
        assureIdConsistent(dish, dishId);
        service.update(dish, restaurantId);
    }


}
