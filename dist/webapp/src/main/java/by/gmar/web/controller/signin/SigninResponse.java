package by.gmar.web.controller.signin;

import by.gmar.model.user.User;
import by.gmar.model.user.User;
import by.gmar.model.user.User;

/**
 *
 * @author s.kosik
 */
public class SigninResponse {

    private boolean isSignedIn;

    private User user;

    public SigninResponse(boolean signedIn, User user) {
        isSignedIn = signedIn;
        this.user = user;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(boolean signedIn) {
        isSignedIn = signedIn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
