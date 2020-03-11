package by.gmar.service.impl.user;

import by.gmar.dataaccess.UserRepository;
import by.gmar.model.user.User;
import by.gmar.model.user.credentials.TokenAuthentication;
import by.gmar.service.user.IRoleService;
import by.gmar.service.user.IUserService;
import by.gmar.utilities.DataWriterUtile;
import by.gmar.utilities.SysUtilities;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


/**
 * @author s.kosik
 */
@Service
public class UserService implements IUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final static String USER_HOME_DIR_VAR = "userHomeDir";
    private final static String DATA_REPOSITORY_VAR = "oDriveDataRepositoryPath";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment environment;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Transactional @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        User user = null;
        try {
            user = userRepository.findByEmail(auth.getName());
        } catch (Exception e) {
            LOGGER.error(e.toString());
            e.printStackTrace();
        }
        boolean passed = false;
        if (auth.getCredentials() instanceof String) {
            passed = passwordEncoder.matches((String) auth.getCredentials(), user.getPassword());
        }
        if (auth instanceof TokenAuthentication) {
            passed = true;
        }
        if (passed) {
            user.setAuthorities(roleService.getAuthoritiesForRole(user.getRole()));
            return new UsernamePasswordAuthenticationToken(user, 
                auth.getCredentials(), user.getAuthorities());
        }

        throw new BadCredentialsException("Bad Credentials");
    }

    @Override @Transactional
    public User create(User user) {//NOTE MERGE WITH SIGNIN SERVICE
        if(null == user){
            return null;
        }
        try {
        user = userRepository.save(user);
    
        String usersDataDir = environment.getProperty(DATA_REPOSITORY_VAR) + 
            environment.getProperty(USER_HOME_DIR_VAR);
        
          SysUtilities.mkDir(usersDataDir, "/" + user.getId().toString());
            String filePath = usersDataDir + "/" + "userAvatar.jpg";
            URL avatarUrl = new File(filePath).toURI().toURL();
            DataWriterUtile.
                    copy(avatarUrl, new File(usersDataDir + "/" + user.getId().toString() + "/userAvatar.jpg"));
            
        } catch (Exception e) {
            LOGGER.error(e.toString());
        
        }
        
        //TODO send invitation letter email/sms
        
        return user;
    }
    
    @Transactional(propagation = Propagation.NESTED)
    @Override public User getById(final Long id) {
        User user = userRepository.findOne(id);
        if(null != user){
            user.setPassword("********");//due to null cause of constraint violation exception
        }
        return user;
    }

}