package by.gmar.web.controller.signin;

import by.gmar.model.user.User;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilder;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import by.gmar.service.ODKeys;
import by.gmar.service.user.IUserDevice;
import by.gmar.web.WebConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author s.kosik
 */
@Component
public class SigninUtilite {
    private static final Logger LOGGER = LoggerFactory.getLogger(SigninUtilite.class);

    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;
    @Autowired
    private IUserDevice userDevices;

    public Response successfullLoginResponse(final User user, final String appToken, String label) {
        final ResponseBuilder builder
                = responseBuilder.instance().addObject(ODKeys.ID, user.getId())
                .addObject(WebConstants.REDIRECT,
                        WebConstants.SECURE + WebConstants.FIT_PAGE);
        
        if(null != appToken){
            userDevices.save(user, appToken, label);
        }
        
        return builder.build();
    }
    
    public String getGrantedAuthorities() {
        StringBuilder grantedAuthorities = new StringBuilder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                grantedAuthorities.append(authority.getAuthority());
                grantedAuthorities.append(" ");
            }
        }
        return grantedAuthorities.toString();
    }    
}
