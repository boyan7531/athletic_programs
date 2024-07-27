package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserSession userSession;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserSession userSession, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }

    public boolean login(UserLoginBindingModel userLoginBindingModel) {
        Optional<User> byUsername = userRepository.findByUsername(userLoginBindingModel.getUsername());
        if(byUsername.isEmpty()){
            return false;
        }
        boolean matches = passwordEncoder.matches(userLoginBindingModel.getPassword(), byUsername.get().getPassword());
        if(!matches){
            return false;
        }
        userSession.login(userLoginBindingModel.getUsername());
        userSession.setLogged(true);
        return true;
    }
}
