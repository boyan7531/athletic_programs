package bg.softuni.athleticprogramapplication.entities.dto.binding;

//import bg.softuni.athleticprogramapplication.validation.annotations.PasswordMatch;
//import bg.softuni.athleticprogramapplication.validation.annotations.UniqueEmail;
//import bg.softuni.athleticprogramapplication.validation.annotations.UniqueUsername;

import bg.softuni.athleticprogramapplication.validation.annotations.PasswordMatch;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueEmail;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@PasswordMatch
public class UserRegisterBindingModel {
    @NotNull(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username length must be between 2 and 20 characters")
    @UniqueUsername
    private String username;
    @NotNull(message = "Full name is required")
    @Size(min = 2, max = 40, message = "Full name length must be between 2 and 40 characters")
    private String fullName;
    @NotNull(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password size must be between 6 and 20 characters")
    private String password;
    @NotNull
    @Size(min = 6,max = 20, message = "")
    private String confirmPassword;
    @NotNull(message = "Email is required")
    @UniqueEmail
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public @NotNull String getFullName() {
        return fullName;
    }

    public void setFullName(@NotNull String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
