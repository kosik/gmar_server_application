package by.gmar.model.user;

import by.gmar.model.BaseEntity;
import by.gmar.model.assets.Appartement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author s.kosik
 */
@Entity
@Table(name = "private_codes", uniqueConstraints = @UniqueConstraint(columnNames = {"code"},
    name = "UK_PIN_CODE")
)
public class PrivateCode extends BaseEntity {
    private Long id;
    private Appartement appartement;
    private String code;
    private Boolean comprometation = false;

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_PIN_REF"), nullable = false)
    public Appartement getAppartement() {
            return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    @Column(updatable = false, length = 36)//uuid length
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getComprometation() {
        return comprometation;
    }

    public void setComprometation(Boolean comprometation) {
        this.comprometation = comprometation;
    }
}