package by.gmar.web.intercept;

import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.service.ODKeys;
import by.gmar.web.WebConstants;
import by.gmar.web.security.UserHolder;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.model.user.User;
import by.gmar.service.ODKeys;
import by.gmar.web.WebConstants;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import by.gmar.web.security.UserHolder;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author s.kosik
 */
@Component
public class CustomAccessDenyResponseFilter extends GenericFilterBean {

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;

    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final User user = userHolder.getUser();
        
        HttpServletRequest httpReq = (HttpServletRequest)request;
        
        if (null == user && httpReq.getMethod().equalsIgnoreCase("post")) {
            final Response responseMsg = responseBuilder.instance().indicateFailure()
                    .addErrorMessageWithCode(ODKeys.ACCESS_DENIED,
                            ErrorCodes.AccessDenied.getValue()).build();

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getWriter(),
                    responseMsg);

            return;
        }
        
        if(null == user && httpReq.getMethod().equalsIgnoreCase("get")){
            HttpServletResponse httpResp = (HttpServletResponse)response;
            httpResp.sendRedirect(WebConstants.SLASH + WebConstants.CONTENT + WebConstants.FLOW);
            return;
        }

        chain.doFilter(request, response);
    }
    
}
