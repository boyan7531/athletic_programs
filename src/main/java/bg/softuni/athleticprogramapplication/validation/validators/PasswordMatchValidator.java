package bg.softuni.athleticprogramapplication.validation.validators;

import bg.softuni.athleticprogramapplication.entities.dto.binding.UserRegisterBindingModel;
import bg.softuni.athleticprogramapplication.validation.annotations.PasswordMatch;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, UserRegisterBindingModel> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegisterBindingModel userRegisterBindingModel, ConstraintValidatorContext context) {
        final String password = userRegisterBindingModel.getPassword();
        final String confirmPassword = userRegisterBindingModel.getConfirmPassword();

        if(password == null || confirmPassword == null) {

            return false;
        }else{
            boolean passwordMatch = password != null && password.equals(confirmPassword);
            if(!passwordMatch){
                HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);

                hibernateContext
                        .buildConstraintViolationWithTemplate("Passwords do not match")
                        .addPropertyNode("confirmPassword")
                        .addConstraintViolation()
                        .disableDefaultConstraintViolation();
            }

            return passwordMatch;
        }
    }
}
