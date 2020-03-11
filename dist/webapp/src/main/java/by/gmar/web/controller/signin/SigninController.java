package by.gmar.web.controller.signin;

import by.gmar.exceptions.AddressNotAvailableException;
import by.gmar.exceptions.ErrorCodes;
import by.gmar.exceptions.UserLoginException;
import by.gmar.model.user.User;
import by.gmar.utilities.RandomCharactersUtilite;
import by.gmar.utilities.SysUtilities;
import by.gmar.web.controller.messages.Response;
import by.gmar.web.controller.messages.ResponseBuilderFactoryBean;
import by.gmar.web.WebConstants;
import by.gmar.service.user.ISigninService;
import by.gmar.service.ODKeys;
import by.gmar.web.security.UserHolder;
//import by.gmar.service.payments.IPayService;
import by.gmar.service.user.IUserDevice;

import java.text.ParseException;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *
 * @author s.kosik
 */
@Controller(value = "signinController")
public class SigninController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SigninController.class);
    private final String EMAIL_IS_BYSY = "emailBusy";
    private final String PERSONAL_DATA_CONSTRAINT_MSG = "personalDataConstraint";
    private final String SOMETHING_WENT_WRONG_MSG = "somethingWentWrong";

    private final String DOMAIN_PREFIX = "@gmar.by";

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private ResponseBuilderFactoryBean responseBuilder;
    
    @Autowired
    @Qualifier("DefSigninService")
    private ISigninService signinService;
    @Autowired 
    private Environment env;
    @Autowired
    private SigninUtilite signinUtilite;

    @Autowired
    private IUserDevice deviceService;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @RequestMapping(value = WebConstants.SIGNIN_PAGE_URL)
    public String signin() {
        return WebConstants.SIGNIN_INTERNAL_REDIRECT_PAGE_URL;
    }


    /**
     * We have to add this in order to catch in filters and being more particular Binnary Repo Filter
     * @param request
     */
    private void repositoryFilterHandler(final HttpServletRequest request){
        final HttpSession session = request.getSession(true);
        final SecurityContext sc = SecurityContextHolder.getContext();
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
    }

    @RequestMapping(value = WebConstants.AUTOLOGIN_URL, method = RequestMethod.POST)
    @ResponseBody
    public Response autologin(@RequestParam(value = "appToken", required = true) final String appToken,
                              @RequestParam(value = "id", required = true) final Long userId,
                              @RequestParam(value = "instantToken", required = true)final String fireBaseToken,
                              @RequestParam(value = "phoneNumber", required = false)final String phoneNumber,
                              final HttpServletRequest request, BindingResult result) throws Exception {
        Authentication authentication = null;
        try {
            LOGGER.trace("id " + userId + " appToken "+ appToken + " /instantToken " + fireBaseToken);

            if(null == phoneNumber || phoneNumber.equals("")){
                authentication = signinService.autologin(userId, appToken, fireBaseToken);
            } else {
                if(!SysUtilities.validatePhone(phoneNumber)){
                    throw new UserLoginException("invalid phone format");
                }
                authentication = signinService.autologin(phoneNumber, appToken, fireBaseToken);
            }
            
        } catch (UserLoginException e) {
            String msg = env.getProperty(ODKeys.WRONG_INCOME_DATA_KEY);
            LOGGER.error(msg);
            e.printStackTrace();
            return responseBuilder.instance().
                addErrorMessageWithCode(ODKeys.WRONG_INCOME_DATA_KEY, 
                    ErrorCodes.UserLoginException.getValue()).build();
        }
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        repositoryFilterHandler(request);

        final User user = userHolder.getUser();
        LOGGER.debug("Successfully auto-authenticated user {} (granted authorities: {})", 
            user.getUsername(), signinUtilite.getGrantedAuthorities());
         
        return signinUtilite.successfullLoginResponse(user, null, null);
    }
    
    @RequestMapping(value = WebConstants.AUTOREGISTRATION_URL, method = RequestMethod.POST)
    @ResponseBody
    public Response autogegistration(@RequestParam(value = "appToken", required = true) final String appToken,//imae
            @RequestParam(value = "instantToken", required = true)final String fireBaseToken, 
            @RequestParam(value = "label", required = true)final String deviceLabel,
            @RequestParam(value = "email", required = false)String identityKey,
            final Locale locale,
             final HttpServletRequest request, BindingResult result) throws Exception {

        Authentication authentication = null;
        try {
            LOGGER.trace(" appToken "+ appToken + " /instantToken " + fireBaseToken);
        User user = new User();
        //TODO check DB email appToken concistency
        if(null == identityKey || "".equals(identityKey)){
            identityKey = UUID.randomUUID().toString()+DOMAIN_PREFIX;
        }

        
        user.setPassword(new RandomCharactersUtilite().randomString(8));
        user.setIdentityKey(identityKey);
        user.setInstantToken(fireBaseToken);
        
        user = signinService.register(user);
        deviceService.save(user, appToken, deviceLabel);
    
        authentication = signinService.autologin(user.getId(), 
            passwordEncoder.encode(new StringBuffer(appToken).reverse().toString()), 
                fireBaseToken);        

        } catch (AddressNotAvailableException eanae){
            LOGGER.error("attemp autoregistration with already registered data " + 
                    identityKey + " " + appToken);
            
            return responseBuilder.instance().indicateFailure().build();

        } catch (Exception e) {
            String msg = env.getProperty(ODKeys.WRONG_INCOME_DATA_KEY);
            LOGGER.error(msg);
            e.printStackTrace();
            return responseBuilder.instance().
                addErrorMessageWithCode(ODKeys.WRONG_INCOME_DATA_KEY, 
                    ErrorCodes.UserLoginException.getValue()).build();
        }
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        repositoryFilterHandler(request);

        final User user = userHolder.getUser();
        LOGGER.debug("Successfull auto-registration user {} (granted authorities: {})", 
            user.getUsername(), signinUtilite.getGrantedAuthorities());
         
        return signinUtilite.successfullLoginResponse(user, appToken, deviceLabel);
    }//autogegistration
    
    @RequestMapping(value = WebConstants.LOGIN_URL2, method = RequestMethod.POST)
    @ResponseBody
    public Response login2(@RequestParam(value = "pass", required = true) final String password,
            @RequestParam(value = "userMail", required = true) final String identityKey,//email, phone
            @RequestParam(value = "appToken", required = false) final String appToken,
            @RequestParam(value = "label", required = false) final String label,
            @RequestParam(value = "instantToken", required = false)final String fireBaseToken,
           final HttpServletRequest request, BindingResult result) {
        try {
            User user = passwordLogin(identityKey, password, fireBaseToken);
            repositoryFilterHandler(request);
            return signinUtilite.successfullLoginResponse(user, appToken, label);
        } catch (AuthenticationException e) {
            LOGGER.warn("Authentication error for user '{}', reason: {}", identityKey, e.toString());
            return responseBuilder.instance().indicateFailure().build();
        } catch (Exception ex){
            LOGGER.warn("Authentication error for user '{}', reason: {}", identityKey, ex.toString());
            return responseBuilder.instance().indicateFailure().build();
        }
    }
    
    @ResponseBody
    @RequestMapping(value = WebConstants.SIGNIN_REGISTER_URL, method = RequestMethod.POST)
    public Response register(@Valid User user,
            @RequestParam(value = "appToken", required = false) final String appToken,
            @RequestParam(value = "label", required = false) final String label,
	@RequestParam(value = "instantToken", required = false)final String fireBaseToken,
            HttpServletRequest req, BindingResult result) throws Exception {
        LOGGER.debug(user.toString());

        if(result.hasErrors()){
            return responseBuilder.instance().addErrorsByKeys(result).build();
        }
        LOGGER.debug("label " + label);
        try {
            final String pass = user.getPassword();

            signinService.register(user);
            user = passwordLogin(user.getUsername(), pass, user.getInstantToken());
        
            try {//NOTE: trial activation. 
                //TODO: fix pom cyclic reference between core and payments issue in order to execute 
                //payment services as part of businesslogic
//                paySrv.internalPay(user, 3l);
            } catch (Exception e) {}
            
            
        } catch (AddressNotAvailableException notAvailable){
            LOGGER.error(notAvailable.toString());
            return responseBuilder.instance().
                    addErrorMessageWithCode(EMAIL_IS_BYSY, 
                    ErrorCodes.EmailExist.getValue()).build();
        }
        catch (RuntimeException e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
            return responseBuilder.instance().
                addErrorMessageWithCode(SOMETHING_WENT_WRONG_MSG, 
                    ErrorCodes.Unknown.getValue()).build();
        } catch (Exception ex) {
            LOGGER.warn("Register error for user '{}', reason: {}", user.getUsername(), ex.toString());
            return responseBuilder.instance().indicateFailure().build();
        }

        repositoryFilterHandler(req);
        return signinUtilite.successfullLoginResponse(user, appToken, label);
    }

    private User passwordLogin(final String username, final String password,
            final String fireBaseToken) throws Exception {
        LOGGER.debug("LOGIN Authentication");
        final Authentication authentication = signinService
                .signinWithPassword(username, password, fireBaseToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        LOGGER.debug("Successfully authenticated user {} (granted authorities: {})",
                username, signinUtilite.getGrantedAuthorities());
        return (User) authentication.getPrincipal();
    }

}
