package bg.softuni.athleticprogramapplication.config;

import bg.softuni.athleticprogramapplication.entities.User;
import bg.softuni.athleticprogramapplication.entities.Program;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
public class UserSession {
    private long id;
    private String username;
    private String fullName;
    private boolean isLogged;
    private User currentUser;

    public void login(Long id,String username){
        this.id = id;
        this.username = username;
        this.isLogged = true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isLoggedIn(){
        return isLogged;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    // New method to set program in session
    public void setProgram(Program program) {
        if (this.currentUser != null) {
            this.currentUser.setProgram(program);
        }
    }

    // New method to get program from session
    public Program getProgram() {
        return this.currentUser != null ? this.currentUser.getProgram() : null;
    }
}
