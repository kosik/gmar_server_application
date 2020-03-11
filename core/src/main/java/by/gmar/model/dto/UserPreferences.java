package by.gmar.model.dto;

import by.gmar.model.user.User;

import java.io.Serializable;

/**
 *
 * @author s.kosik
 */
public class UserPreferences implements Serializable {
    
    private User user;
    private boolean googleContactsImporting = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isGoogleContactsImporting() {
        return googleContactsImporting;
    }

    public void setGoogleContactsImporting(boolean googleContactsImporting) {
        this.googleContactsImporting = googleContactsImporting;
    }

}
