package local.vda.votingsystem.service;


import local.vda.votingsystem.model.User;
import local.vda.votingsystem.to.UserTo;
import local.vda.votingsystem.util.exception.NotFoundException;

import java.util.List;

public interface UserService {
    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    void enable(int id, boolean enable);
}