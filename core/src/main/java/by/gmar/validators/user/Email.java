package by.gmar.validators.user;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author s.kosik
 */
public class Email {

    public static final String EMAIL_FORMAT_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @NotBlank
    @org.hibernate.validator.constraints.Email(regexp = EMAIL_FORMAT_REGEXP)
    @UniqueEmail(groups = SignupValidationGroup.class)
    @ExistingEmail(groups = ForgotPasswordValidationGroup.class)
    private String email;

    public Email() {
        super();
    }

    public Email(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
