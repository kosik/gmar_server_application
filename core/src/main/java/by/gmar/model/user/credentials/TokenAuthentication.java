package by.gmar.model.user.credentials;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author s.kosik
 */
public class TokenAuthentication extends AbstractAuthenticationToken{
    private final Object principal;

    private String md5Pass;

    public TokenAuthentication(final String md5Pass, final Object principal) {
        super(null);
        this.md5Pass = md5Pass;
        this.principal = principal;
        setAuthenticated(true);
    }

    public TokenAuthentication(final String md5Pass, final Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.md5Pass = md5Pass;
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return md5Pass;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getAccessToken() {
        return md5Pass;
    }

    public void setAccessToken(String accessToken) {
        this.md5Pass = accessToken;
    }
}
