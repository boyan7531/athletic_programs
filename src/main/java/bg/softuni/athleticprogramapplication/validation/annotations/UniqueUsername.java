package bg.softuni.athleticprogramapplication.validation.annotations;

import bg.softuni.athleticprogramapplication.validation.validators.UniqueUsernameValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueUsernameValidator.class})
public @interface UniqueUsername {
    String message() default "{user.username.unique}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
