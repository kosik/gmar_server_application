package by.gmar.web.intercept;

import by.gmar.exceptions.ErrorCodes;
import by.gmar.exceptions.PasswordValidation;
import by.gmar.model.user.User;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.exceptions.PasswordValidation;
import by.gmar.model.user.User;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.exceptions.PasswordValidation;
import by.gmar.model.user.User;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author s.kosik
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final String PASSWORD_VALIDATION_EX = "by.gmar.validators.user.PasswordRules";
    @Autowired
    private Environment environment;
    
    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;
    
    @ResponseBody
    @ExceptionHandler(PasswordValidation.class)
    public Response handlePasswordValidationException() {
        return responseBuilder.instance().indicateFailure().
                addErrorMessageWithCode(PASSWORD_VALIDATION_EX, 
                        ErrorCodes.PasswordValidation.getValue()).build();
    }

//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(HttpServletRequest req, Exception exception) {
//        LOGGER.error(constructErrorMesssage(req), exception);
//        return new ModelAndView("redirect:/error");
//    }
    
    private String constructErrorMesssage(HttpServletRequest req) {
        String format = "Global exception handler (requested url was: %s; logged user: %s)";
        String username = "unknown";
        if (req.getSession().getAttribute("user") != null) {
            User loggedUser = (User) req.getSession().getAttribute("user");
            username = loggedUser.getUsername();
        }
        return String.format(format, req.getRequestURL(), username)+" and user-agent "+this.extractHeaderInformation(req);
    }
    
        private String extractHeaderInformation(HttpServletRequest req){
        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder stringBuilder  =new StringBuilder();
        while (headerNames.hasMoreElements()) {

            String headerName = headerNames.nextElement();
            stringBuilder.append(headerName);
            stringBuilder.append("\n");

            Enumeration<String> headers = req.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                stringBuilder.append("\t").append(headerValue);
                stringBuilder.append("\n");
            }

        }
        return stringBuilder.toString();
    }
}
