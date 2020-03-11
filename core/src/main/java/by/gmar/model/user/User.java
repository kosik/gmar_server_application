package by.gmar.model.user;

import com.google.common.collect.Collections2;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import by.gmar.model.BaseEntity;
import by.gmar.model.commons.UserRole;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Size;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//TODO put log-ed users into second-level cache due to not hit DB too much

/**
 *
 * @author s.kosik
 */
@Entity
@Table(name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = {"identityKey"},
    name = "UK_CONTACT_ITEM")
)
public class User extends BaseEntity implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;
    
    private transient final String PASS_VALIDATION_KEY = "passwordValidationMsg";
    private transient final String EMAIL_VALIDATION_KEY = "emailValidationMsg";
    private transient final String LENGTH_VALIDATION_KEY = "lengthValidation";

    public transient static final String EMAIL_FORMAT_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static Function<String, SimpleGrantedAuthority> toGrantedAuthority
        = new Function<String, SimpleGrantedAuthority>() {
            @Override
            public SimpleGrantedAuthority apply(String from) {
                return new SimpleGrantedAuthority(from);
            }
    };

    private Long id;
    private String privacyPermissions;
    private String identityKey;//Might be email, phone number or any other unique synthetic key
    private String password;
    private boolean verified = false;
    private boolean readOnly = false;
    private UserRole role;

    private Collection<UserDevice> userDevices;

    private Collection<ResidencyAddress> address;

    private String instantToken;//NOTE: fireBaseToken is updating each time 
    //during mobile client login. This field should not downstream back to
    //client side due to security reasons
    
    private String host;
    
    @Transient
    private transient List<? extends GrantedAuthority> authorities;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))
                || !StringUtils.equals(this.identityKey, other.identityKey)) {
            return false;
        }
        return true;
    }
    
    public User(){}
    public User(final Long uid){
        this.id = uid;
    }
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    @NotBlank
    @Email(regexp = EMAIL_FORMAT_REGEXP, message = EMAIL_VALIDATION_KEY)
    @Column(length = 100, updatable = true)
    @Basic(optional = false)
    public String getIdentityKey() {
        return identityKey;
    }

    public void setIdentityKey(final String identityKey) {
        this.identityKey = identityKey;
    }

    @JsonIgnore
    @Override
    @NotBlank
    @Size(min = 8, message = PASS_VALIDATION_KEY)    
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)    
    public UserRole getRole() {
        if (this.role == null) {
            return UserRole.USER;
        }
        return role;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
    public Collection<UserDevice> getUserDevices() {
        return userDevices;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    public Collection<ResidencyAddress> getAddress() {
        return address;
    }

    @Column(nullable = false)
    public boolean isVerified() {
        return verified;
    }
    
    @JsonIgnore @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    public void setAccountNonExpired(boolean flag){}
    public void setAccountNonLocked(boolean flag){}
    public void setCredentialsNonExpired(boolean flag){}
    public void setEnabled(boolean flag){}
    @Transient public void setUsername(String name){}
    
    @JsonIgnore @Override @Transient
    public String getUsername() {
        return identityKey;
    }
    
    @JsonIgnore @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore @Override
    public boolean isEnabled() {
        return true;
    }    

    @JsonIgnore
    public String getInstantToken() {
        return instantToken;
    }
    
    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Transient
    public void setAuthorities(Collection<String> authorities) {
        this.authorities = ImmutableList.copyOf(
            Collections2.transform(authorities, toGrantedAuthority));
    }

    public String getPrivacyPermissions() {
        return privacyPermissions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrivacyPermissions(final String privacyPermissions) {
        this.privacyPermissions = privacyPermissions;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }    
    
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }    

    public void setAddress(Collection<ResidencyAddress> address) {
        this.address = address;
    }

    public void setUserDevices(Collection<UserDevice> userDevices) {
        this.userDevices = userDevices;
    }

    public void setInstantToken(final String firebaseRefreshToken) {
        this.instantToken = firebaseRefreshToken;
    }
    
    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", privacyPermissions=" + privacyPermissions + ", email=" + identityKey + ", password=" + password + ", verified=" + verified + ", readOnly=" + readOnly + ", role=" + role + '}';
    }

    @Size(max = 50, message = LENGTH_VALIDATION_KEY)
    @Column(length = 50)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
