package local.vda.votingsystem.web;

import local.vda.votingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    private final UserService userService;

    @Autowired
    public RootController(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    private DishService mealService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:meals";
    }

//    @GetMapping("/meals")
//    public String meals(Model model) {
//        model.addAttribute("meals", MealsUtil.getWithExcess(
//                mealService.getByDate(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()
//        ));
//        return "meals";
//    }
}
