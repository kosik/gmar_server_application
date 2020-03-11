package by.gmar.model.assets;


import by.gmar.model.BaseEntity;
import by.gmar.model.user.PrivateCode;
import by.gmar.model.user.ResidencyAddress;
import by.gmar.model.user.User;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Table(name = "appartements")
public class Appartement extends BaseEntity {

    //    private apartment avatar whether it should be dynamic ?

    private Long id;

    private boolean enabled;
    private Integer flatNumber;
    private String RIFIDTag;

    private ResidencyAddress address;

    private DoorControllerPannel pannel;

    private Collection<PrivateCode> privateCodes;

    private Collection<Cohabitant> cohabitants;


    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getRIFIDTag() {
        return RIFIDTag;
    }

    public void setRIFIDTag(String RIFIDTag) {
        this.RIFIDTag = RIFIDTag;
    }


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_APPARTEMENT_ADDRESS"), nullable = false)
    public ResidencyAddress getAddress() {
        return address;
    }

    public void setAddress(ResidencyAddress address) {
        this.address = address;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_DOOR_CONTROLLER"), nullable = false)
    public DoorControllerPannel getPannel() {
        return pannel;
    }

    public void setPannel(DoorControllerPannel pannel) {
        this.pannel = pannel;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appartement", fetch = FetchType.LAZY)
    public Collection<PrivateCode> getPrivateCodes() {
        return privateCodes;
    }

    public void setPrivateCodes(Collection<PrivateCode> privateCodes) {
        this.privateCodes = privateCodes;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appartement", fetch = FetchType.LAZY)
    public Collection<Cohabitant> getCohabitants() {
        return cohabitants;
    }

    public void setCohabitants(Collection<Cohabitant> cohabitants) {
        this.cohabitants = cohabitants;
    }
}
