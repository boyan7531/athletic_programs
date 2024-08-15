package bg.softuni.athleticprogramapplication.validation.validators;

import bg.softuni.athleticprogramapplication.service.UserService;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueUsername;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;
    private String message;

    public UniqueUsernameValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }else{
            final boolean isUnique =userService.isUniqueUsername(value);
            if(!isUnique) replaceDefaultConstraintViolation(context, "Username is already used");
            return isUnique;
        }

    }
    private void replaceDefaultConstraintViolation (ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate("Username is already used")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
