package by.gmar.validators.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author s.kosik
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface ExistingEmail {

    String message() default "{by.gmar.validators.user.ExistingEmail}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}