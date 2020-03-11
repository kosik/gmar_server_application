package by.gmar.validators.user;

import by.gmar.model.user.User;
import by.gmar.dataaccess.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author s.kosik
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(final UniqueEmail constraintAnnotation) {}

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {

        if (userRepository == null) {
            return false;
        }

        if (email == null) {
            return false;
        }
        
        final User user = userRepository.findByEmail(email);
        if(null == user){
            return true;
        }
        return false;
    }
}
