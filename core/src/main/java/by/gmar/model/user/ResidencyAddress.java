package by.gmar.model.user;

import by.gmar.model.BaseEntity;
import by.gmar.model.BaseEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author s.kosik
 */
@Entity
@Table(name = "addresses")
public class ResidencyAddress extends BaseEntity {
    private Long id;
    private User user;
    private Country country;
    private String postalCode;
    private String state;
    private String city;
    private String street;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_ADDRESS_USER"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(foreignKey=@ForeignKey(name="FK_COUNTRY", value = ConstraintMode.CONSTRAINT))
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
    @Column(length = 15)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String zip) {
        this.postalCode = zip;
    }
    
    @Column(length = 30)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    @Column(length = 50)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    @Column(length = 50)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
}
