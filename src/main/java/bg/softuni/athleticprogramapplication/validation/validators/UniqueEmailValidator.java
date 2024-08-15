package bg.softuni.athleticprogramapplication.validation.validators;

import bg.softuni.athleticprogramapplication.service.UserService;
import bg.softuni.athleticprogramapplication.validation.annotations.UniqueEmail;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private String message;

    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }else{
            final boolean isUnique = userService.isUniqueEmail(value);

            if(!isUnique) replaceDefaultConstraintViolation(context, "Email is already used");
            return isUnique;
        }

    }
    private void replaceDefaultConstraintViolation (ConstraintValidatorContext context, String message) {

        context
                .unwrap(HibernateConstraintValidatorContext.class)
                .buildConstraintViolationWithTemplate("Email is already used")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
