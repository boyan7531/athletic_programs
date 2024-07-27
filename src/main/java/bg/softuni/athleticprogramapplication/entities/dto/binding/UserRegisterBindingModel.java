package bg.softuni.athleticprogramapplication.entities.dto.binding;

import bg.softuni.athleticprogramapplication.validation.annotations.PasswordMatch;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueEmail;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@PasswordMatch
public class UserRegisterBindingModel {
    @NotNull
    @UniqueUsername
    @Size(min = 2, message = "{user.username.length}")
    private String username;
    @NotNull
    @Size(min = 2,message = "{user.full-name.length}")
    private String fullName;
    @NotNull
    @Size(min = 2, message = "{user.password.length}")
    private String password;
    @NotNull
    @Size(min = 2, message = "{user.confirm-password.length}")
    private String confirmPassword;
    @NotNull
    @UniqueEmail
    @Email(regexp = ".+[@].+", message = "{user.email}")
    private String email;
    @NotNull
    @Positive(message = "{user.age}")
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
