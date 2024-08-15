package bg.softuni.athleticprogramapplication.service.impl;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserSession userSession;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository, UserSession userSession, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;

        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean isUniqueUsername(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }
    @Override
    public boolean login(UserLoginBindingModel userLoginBindingModel) {
        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(userLoginBindingModel.getUsername());

        // Check if user is present
        if (userOptional.isEmpty()) {
            return false; // User not found
        }

        User user = userOptional.get();

        // Check if password matches
        boolean matches = passwordEncoder.matches(userLoginBindingModel.getPassword(), user.getPassword());
        if (!matches) {
            return false; // Password mismatch
        }

        // Log in user (assuming userSession is a service to handle session state)
        userSession.login(user.getId(), user.getUsername());
        userSession.setCurrentUser(user);
        userSession.setLogged(true);

        return true;
    }

    @Override
    public User getUserById(Long userId) {
        return this.userRepository.getById(userId);
    }

    @Override
    public User getUserWithProgram(Long userId) {
        return userRepository.findUserWithProgramById(userId);
    }

    @Override
    public void register(UserRegisterBindingModel userRegisterBindingModel) {
        User user = new User();
        user.setUsername(userRegisterBindingModel.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setFullName(userRegisterBindingModel.getFullName());

        this.userRepository.saveAndFlush(user);


//        userRepository.save(map(userRegisterBindingModel));
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

//    @Override
//    public Optional<User> save(Optional<User> currentUser) {
//        return userRepository.save(currentUser);
//    }
//    private User map(UserRegisterBindingModel userRegisterBindingModel) {
//        User mappedEntity = modelMapper.map(userRegisterBindingModel, User.class);
//
//        mappedEntity.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
//
//        return mappedEntity;
//    }
}
