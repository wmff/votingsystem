package local.vda.votingsystem.web.restaurant;

import local.vda.votingsystem.model.Restaurant;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestaurantRestController.REST_URL)
public class RestaurantRestController extends AbstractRestaurantController {
    static final String REST_URL = "/rest/restaurants";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllWithDishesToday() {
        return super.getAllWithDishesToday();
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getByName(@RequestParam String name) {
        return super.getByName(name);
    }
}