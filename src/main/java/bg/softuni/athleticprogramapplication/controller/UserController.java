package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.exceptions.LoginCredentialsException;
import bg.softuni.athleticprogramapplication.repositories.UserRepository;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private final UserService userService;
    private final UserSession userSession;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserSession userSession, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

//    @ModelAttribute("userLoginBindingModel")
//    public UserLoginBindingModel userLoginBindingModel() {
//        return new UserLoginBindingModel();
//    }

    @GetMapping("/users/login")
    public String viewLogin() {
        if(userSession.isLoggedIn()){
            return "redirect:/";
        }
       return "login";
    }



    @PostMapping("/users/login")
    public String doLogin(@Valid UserLoginBindingModel userLoginBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if(userSession.isLoggedIn()){
            return "redirect:/";
        }

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("loginError", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:/users/login";
        }

        boolean success = userService.login(userLoginBindingModel);
        if(!success){
            redirectAttributes.addFlashAttribute("loginError", userLoginBindingModel);
            return "redirect:/users/login";
        }
        userSession.setLogged(true);

        return "redirect:/";

    }


//    @GetMapping("/login")
//    public ModelAndView login() {
//
//        return new ModelAndView("login");
//    }





    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @GetMapping("/users/register")
    public String register(){
        if(userSession.isLoggedIn()){
            return "redirect:/";
        }
        return "register";
    }


    @PostMapping("/users/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:/users/register";
        }

        this.userService.register(userRegisterBindingModel);
        return "redirect:/users/login";
    }


    @GetMapping("/users/logout")
    public String logout(){
        if(!userSession.isLoggedIn()){
            return "redirect:/";
        }
        userSession.setLogged(false);
        return "redirect:/users/logout";
    }

    @GetMapping("/about")
    public String aboutUs(){
        return "about";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Long userId = userSession.getId();
        Optional<User> userById = userService.findById(userId);
        User user = new User();
        if(userById.isPresent()){
            user = userById.get();
        }
        String programTitle = (user.getProgram() != null) ? user.getProgram().getTitle() : "No Program Assigned";
        model.addAttribute("username", user.getUsername());
        model.addAttribute("enrolledProgram",programTitle);
        model.addAttribute("runs", user.getRuns());
        model.addAttribute("favoriteMeals", user.getFavoriteMeals());

        return "profile";
    }

    @GetMapping("/users/change-username")
    public String showChangeUsernameForm() {
        return "change-username";
    }

    @PostMapping("/users/change-username")
    public String changeUsername(@RequestParam("newUsername") String newUsername,
                                 @RequestParam("password") String password,
                                 Model model) {
        Long currentUserId = userSession.getId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("error", true);
            return "change-username";
        }


        boolean isUsernameChanged = userService.changeUsername(user, newUsername);

        if (isUsernameChanged) {
            return "redirect:/profile";
        } else {
            model.addAttribute("error", true);
            return "change-username";
        }

    }
}
