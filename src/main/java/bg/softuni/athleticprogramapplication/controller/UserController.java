package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.exceptions.LoginCredentialsException;
import bg.softuni.athleticprogramapplication.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";
    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
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
    public String getProfile(Model model){

        if(!userSession.isLoggedIn()){
            return "redirect:/users/login";
        }


        Long userId = userSession.getId();
        User user = userService.getUserWithProgram(userId);
        model.addAttribute("user", user);
        return "profile";
    }


}
