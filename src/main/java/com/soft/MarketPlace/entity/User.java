package com.soft.MarketPlace.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "IDUSER")
    private int idUser;
    @Column(name = "NOM" , nullable = false)
    private String nom;
    @Column(name = "PRENOM")
    private String prenom;
    @Column(name = "DATENAISSANCE", nullable = false)
    private String dateNaissance;
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @Column(name = "TELEPHONE1", nullable = false, unique = true)
    private String telephone1;
    @Column(name = "TELEPHONE2", unique = true)
    private String telephone2;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "CONFIRMPASSWORD")
    private String confirmPassword;
    @Column(name = "ISVALIDE")
    private Boolean isValide;

    @Column(name = "ISTELEPHONE1VALIDE")
    private Boolean isTelephone1Valide;

    @Column(name = "ISTELEPHONE2VALIDE")
    private Boolean isTelephone2Valide;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "userrole",
            joinColumns = @JoinColumn(
                    name = "IDUSER", referencedColumnName = "idUser"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "IDROLE", referencedColumnName = "idRole"
            )
    )
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "adresseuser",
            joinColumns = @JoinColumn(name = "IDUSER", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "IDADRESSE", referencedColumnName = "idAdresse")
    )
    private List<Adresse> adresseUser;

    @NotNull
    public void createUserFromClient(@NotNull Client client){
        this.idUser = client.getIdUser();
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
        this.dateNaissance = client.getDateNaissance();
        this.email = client.getEmail();
        this.telephone1 = client.getTelephone1();
        this.telephone2 = client.getTelephone2();
        this.password = client.getPassword();
        this.confirmPassword = client.getConfirmPassword();
        this.isValide = client.getIsValide();
        this.isTelephone1Valide = client.getIsTelephone1Valide();
        this.isTelephone2Valide = client.getIsTelephone2Valide();
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", email='" + email + '\'' +
                ", telephone1='" + telephone1 + '\'' +
                ", telephone2='" + telephone2 + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", isValide=" + isValide +
                ", roles=" + roles +
                ", adresseUser=" + adresseUser +
                '}';
    }
}
