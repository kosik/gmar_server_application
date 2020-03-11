package by.gmar.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author s.kosik
 */
@Entity
@Table(name = "countries")
public class Country {
    private Long id;
    private Integer phoneCode;
    private String countryTag;
    private String countryName;
    private String languageTag;
    
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }    

    @Column(length = 3)
    public String getCountryTag() {
        return countryTag;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public void setCountryTag(String countryTag) {
        this.countryTag = countryTag;
    }

    @Column(length = 40)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Column(length = 4)
    public String getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(String languageTag) {
        this.languageTag = languageTag;
    }
    
}
