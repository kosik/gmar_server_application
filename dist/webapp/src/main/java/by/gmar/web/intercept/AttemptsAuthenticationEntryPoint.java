package by.gmar.web.intercept;

import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.service.ODKeys;
import by.gmar.web.security.UserHolder;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.service.ODKeys;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import by.gmar.web.security.UserHolder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author s.kosik
 */
//@Component
public class AttemptsAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private UserHolder userHolder;
    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;
    
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
            AuthenticationException authException) throws IOException, ServletException {

        final User user = userHolder.getUser();

        if (null == user && request.getMethod().equalsIgnoreCase("post")) {
            final Response responseMsg = responseBuilder.instance().indicateFailure()
                    .addErrorMessageWithCode(ODKeys.ACCESS_DENIED,
                            ErrorCodes.AccessDenied.getValue()).build();

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(),
                    responseMsg);
//            return;
        }
        
    }
    
}
