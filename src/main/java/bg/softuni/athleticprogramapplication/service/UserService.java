package bg.softuni.athleticprogramapplication.service;

import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;

public interface UserService {
    boolean isUniqueUsername(String username);

    boolean isUniqueEmail(String email);

    boolean login(UserLoginBindingModel userLoginBindingModel);

}
