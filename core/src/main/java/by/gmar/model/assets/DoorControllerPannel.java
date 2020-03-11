package by.gmar.model.assets;

import by.gmar.model.BaseEntity;
import by.gmar.model.user.ResidencyAddress;

import javax.persistence.*;

@Entity
@Table(name = "door_controllers")
public class DoorControllerPannel extends BaseEntity {
    private Long id;
    private String vendor;
    private ResidencyAddress address;

    private Appartement appartement;


    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_PANNEL_ADDRESS"), nullable = false)
    public ResidencyAddress getAddress() {
        return address;
    }

    public void setAddress(ResidencyAddress address) {
        this.address = address;
    }

    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }
}
