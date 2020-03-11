package by.gmar.model.assets;

import by.gmar.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "cohabitants")
public class Cohabitant {

    private Long id;
    private Appartement appartement;
    private User member;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_APPARTEMENT"))
    public Appartement getAppartement() {
        return appartement;
    }

    public void setAppartement(Appartement appartement) {
        this.appartement = appartement;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="FK_APPARTEMENT_MEMBER",
            value = ConstraintMode.NO_CONSTRAINT), insertable = true)
    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

}
