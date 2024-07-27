package bg.softuni.athleticprogramapplication.config;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class UserSession {
    private long id;
    private String username;
    private boolean isLogged;

    public void login(String username){
        this.username = username;
        this.isLogged = true;
    }

    public boolean isLoggedIn(){
      return isLogged;
    }

    public Long getId() {
        return id;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }
}
