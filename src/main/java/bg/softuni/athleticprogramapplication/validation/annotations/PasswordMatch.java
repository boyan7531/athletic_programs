package bg.softuni.athleticprogramapplication.validation.annotations;

import bg.softuni.athleticprogramapplication.validation.validators.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PasswordMatchValidator.class})
public @interface PasswordMatch {
    String message() default "{user.password-match}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
