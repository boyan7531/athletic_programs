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
import java.util.Set;

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

        Optional<User> userOptional = userRepository.findByUsername(userLoginBindingModel.getUsername());


        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();


        boolean matches = passwordEncoder.matches(userLoginBindingModel.getPassword(), user.getPassword());
        if (!matches) {
            return false;
        }


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

    @Override
    public Optional<User> findById(Long userId) {
        return this.userRepository.findById(userId);
    }

    public boolean changeUsername(User user, String newUsername) {

        if (user.getUsername().equals(newUsername)) {
            return false; // Username is the same, no change needed
        }


        Optional<User> existingUser = userRepository.findByUsername(newUsername);
        if (existingUser.isPresent()) {
            return false;
        }


        user.setUsername(newUsername);
        userRepository.save(user);
        return true;
    }


    public boolean validatePassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
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
