package bg.softuni.athleticprogramapplication.controller;

import bg.softuni.athleticprogramapplication.config.UserSession;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserLoginBindingModel;
import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.exceptions.LoginCredentialsException;
import bg.softuni.athleticprogramapplication.service.AuthenticationService;
import bg.softuni.athleticprogramapplication.service.UserService;
import bg.softuni.athleticprogramapplication.service.impl.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationService authenticationService;
    private final UserServiceImpl userServiceImpl;
    private final UserSession userSession;

    public UserController(AuthenticationService authenticationService, UserServiceImpl userServiceImpl, UserSession userSession) {
        this.authenticationService = authenticationService;
        this.userServiceImpl = userServiceImpl;
        this.userSession = userSession;
    }

    @ModelAttribute("userLoginBindingModel")
    public UserLoginBindingModel userLoginBindingModel() {
        return new UserLoginBindingModel();
    }

    @GetMapping("/login")
    public ModelAndView viewLogin() {
        ModelAndView modelAndView = new ModelAndView("login");

        modelAndView.addObject("loginData", new UserLoginBindingModel());

        return modelAndView;
    }

//    @GetMapping("/login")
//    public String login(){
//
//        return "/login";
//    }

    @PostMapping("/login")
    public String doLogin(@Valid UserLoginBindingModel userLoginBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){
        if(userSession.isLoggedIn()){
            return "redirect:/home";
        }

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("loginData", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:/login";
        }

        boolean success = authenticationService.login(userLoginBindingModel);
        if(!success){
            redirectAttributes.addFlashAttribute("loginError", userLoginBindingModel);
            return "redirect:/login";
        }

        return "redirect:/home";

    }


//    @GetMapping("/login")
//    public ModelAndView login() {
//
//        return new ModelAndView("login");
//    }


    @ExceptionHandler(LoginCredentialsException.class)
    public ModelAndView handleLoginCredentialsError(LoginCredentialsException e,
                                                    RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("badCredentials", true);
        System.out.println(e.getMessage());
        return new ModelAndView("redirect:/login");
    }




    @ModelAttribute("userRegisterBindingModel")
    public UserRegisterBindingModel userRegisterBindingModel(){
        return new UserRegisterBindingModel();
    }

    @GetMapping("/register")
    public ModelAndView register(Model model){
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return new ModelAndView("register");
    }


    @PostMapping("/register")
    public ModelAndView register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){
        final ModelAndView modelAndView = new ModelAndView();

        if(bindingResult.hasErrors()){

           final String attributeName = "userRegisterBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, userRegisterBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return new ModelAndView("redirect:/register");

        }else{

            this.authenticationService.register(userRegisterBindingModel);
            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }

}
