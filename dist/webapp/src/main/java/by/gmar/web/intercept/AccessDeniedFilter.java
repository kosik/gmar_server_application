package by.gmar.web.intercept;

import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 *
 * @author s.kosik
 */
//@Controller
public class AccessDeniedFilter implements AccessDeniedHandler {

    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, 
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        final Response responseMsg = responseBuilder.instance().indicateFailure()
//            .addErrorMessageWithCode(ODKeys.ACCESS_DENIED,
//                ErrorCodes.AccessDenied.getValue()).build();
//        
//        final String responseString = responseMsg.toString();
//        
//        response.getWriter().append(responseString.
//                subSequence(0, responseString.length()));
    }

}
