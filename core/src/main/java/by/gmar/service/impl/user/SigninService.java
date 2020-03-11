package by.gmar.service.impl.user;

import by.gmar.dataaccess.CountryRepo;
import by.gmar.dataaccess.UserRepository;
import by.gmar.exceptions.AddressNotAvailableException;
import by.gmar.exceptions.UserLoginException;

import by.gmar.model.user.User;
import by.gmar.model.user.UserDevice;
import by.gmar.model.user.credentials.TokenAuthentication;
import by.gmar.service.common.IVerify;
import by.gmar.service.user.ISigninService;
import by.gmar.service.user.IUserDevice;
import by.gmar.service.user.IUserService;

import by.gmar.model.commons.UserRole;
import by.gmar.service.ODKeys;

import by.gmar.emailservices.IEmailService;

import java.text.ParseException;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author s.kosik
 */
@Service
@Qualifier("DefSigninService")
public class SigninService implements ISigninService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SigninService.class);

    private IUserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment environment;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserDevice userDevice;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private CountryRepo countryRepo;
    @Autowired
    private IVerify smsVerifier;

    /**
     *
     * @param identityKey might be phone number, email or any other unique key
     * @param appToken contains md5 udid.
     * @param fireBaseToken
     * @return
     * @throws UserLoginException
     * @throws ParseException
     * @throws Exception
     */
    @Transactional
    @Override
    public Authentication autologin(final String identityKey, final String appToken,
            final String fireBaseToken) throws UserLoginException, ParseException, Exception {

        if(null == fireBaseToken || fireBaseToken.isEmpty())
            throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY);

//        final UserInfo uInfo = uInfoRepo.findByPhoneNumber(phoneNumber);
        
//        if(null == uInfo)
//            throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY);
//        final User user = uInfo.getOwner();
        
//        if(null == user){
//            throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY);
//        }

//        return checkDevice(user, appToken, fireBaseToken);

        return null;
    }    
    
    @Transactional @Override
    public Authentication autologin(final Long userId, final String appToken, 
            final String fireBaseToken) throws UserLoginException, 
            ParseException, Exception {
        
        if(null == fireBaseToken || fireBaseToken.isEmpty())
            throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY); 

        final User user = userRepository.findOne(userId);
        
        if(null == user){
            throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY);
        }

        return checkDevice(user, appToken, fireBaseToken);
    }
    
    private Authentication checkDevice(User user,final String appToken, 
            final String fireBaseToken)throws UserLoginException, ParseException{
        final Collection<UserDevice> uDevices = userDevice.get(user);
        boolean appTokenMatch = false;
        for(UserDevice dev : uDevices){
            final String udid = dev.getUdid();
            String reverse = new StringBuffer(udid).reverse().toString();
            reverse = reverse.toLowerCase();
            LOGGER.debug("reverse udid: " + reverse + "/n appToken " + appToken);
            appTokenMatch = passwordEncoder.matches(reverse, appToken);
            if(appTokenMatch){
                user.setInstantToken(fireBaseToken);
                userRepository.save(user);
                return tokenLoginResponse(user);
            }
        }
        throw new UserLoginException(ODKeys.WRONG_INCOME_DATA_KEY); 
    }
    
    @Override
    public Authentication signinWithPassword(String username, String password,
            final String fireBaseToken) {
        Authentication authentication = getUsernamePasswordAuthentication(username, password);
        authentication = userService.authenticate(authentication);
        LOGGER.debug("login authentication worked out");
        if(null != fireBaseToken){
            //NOTE: here we hit DB Second time during login :(
//            final User user = userRepository.findByEmail(username);
            final User user = (User)authentication.getPrincipal();
            user.setInstantToken(fireBaseToken);
            userRepository.save(user);
        }
        return authentication;
    }

    private UsernamePasswordAuthenticationToken 
        getUsernamePasswordAuthentication(String username, String password) {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    private Authentication tokenLoginResponse(final User user) 
            throws UserLoginException, ParseException {
        if (null == user) {
            throw new UserLoginException("");
        }
        TokenAuthentication fAuth
                = new TokenAuthentication(user.getPassword(), user);
        Authentication authentication
                = authentication = userService.authenticate(fAuth);
        return authentication;
    }

    @Override @Transactional
    public User register(User incomUser) throws Exception, AddressNotAvailableException {
        User user = userRepository.findByEmail(incomUser.getIdentityKey());
        if (user != null) {
            throw new AddressNotAvailableException();
        }

        incomUser.setPassword(passwordEncoder.encode(incomUser.getPassword()));
        if(null == incomUser.getRole()){
        //NOTE A User Registration could start with Friend Social Reference, Invited by Email
            incomUser.setRole(UserRole.USER);
        }
        
        try {
            user = userService.create(incomUser);
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }

        //TODO send registration confirmation email/sms
        return user;
    }
    
    private boolean initializeUserWorkspace(final User user)throws Exception{

        return true;
    }
}