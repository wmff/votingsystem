package local.vda.votingsystem.web.user;

import org.springframework.stereotype.Controller;
import local.vda.votingsystem.model.User;

import static local.vda.votingsystem.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}