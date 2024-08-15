package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;

import java.util.Optional;

public interface UserService {
    boolean isUniqueUsername(String username);

    boolean isUniqueEmail(String email);

    boolean login(UserLoginBindingModel userLoginBindingModel);

    User getUserById(Long userId);

    User getUserWithProgram(Long userId);

    void register(UserRegisterBindingModel userRegisterBindingModel);

    void save(User user);

    /*  Optional<User> save(Optional<User> currentUser);*/

}
