package by.gmar.validators.user;

import by.gmar.utilities.CalendarUtilite;
import by.gmar.utilities.CalendarUtilite;
import java.time.Instant;
import java.util.Date;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author s.kosik
 */
public class BirthdaydValidator implements ConstraintValidator<Birthday, Date> {
    private final int MAX_INTERVAL = 5;
    private final int MIN_INTERVAL = 200;
    
    @Override
    public void initialize(Birthday constraintAnnotation) {
        // TODO Auto-generated method stub

    }

    /**
     * Any registered user should be at least 5 years old age and not older than 200 years ago
     * @param value
     * @param context
     * @return 
     */
    @Override
    public boolean isValid(final Date value, final ConstraintValidatorContext context) {
        if(null == value)
            return false;
        final CalendarUtilite util = new CalendarUtilite();
        final Instant fiveYearsAgo = util.currentYearMinus(MAX_INTERVAL);
        if(!value.toInstant().isBefore(fiveYearsAgo)){
            return false;
        }
        final Instant twoHundredYearsAgo = util.currentYearMinus(MIN_INTERVAL);
        if(value.toInstant().isBefore(twoHundredYearsAgo)){
            return false;
        }
        
        return true;
    }

}
