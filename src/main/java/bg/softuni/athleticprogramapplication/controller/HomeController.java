package bg.softuni.athleticprogramapplication.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }


}
