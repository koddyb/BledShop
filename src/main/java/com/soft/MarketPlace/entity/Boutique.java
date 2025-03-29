package com.soft.MarketPlace.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boutiques")
public class Boutique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBOUTIQUE")
    private int idBoutique;
    @OneToOne
    @JoinColumn(name = "IDUTILISATEUR", nullable = false)
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "IDTYPEBOUTIQUE", nullable = false)
    private TypeBoutique typeBoutique;
    @Column(name = "NOM", nullable = false, unique = true)
    private String nom;
    @Column(name = "EMPLACEMENT", nullable = false)
    private String emplacement;
    @Column(name = "SLOGAN", nullable = false)
    private String slogan;
    @Column(name = "LOGO", nullable = false)
    private String logo;
    @Column(name = "IMMATRICULATIONRCCM", nullable = false, unique = true)
    private String immatriculationRccm;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="boutiquefavorie",
            joinColumns = @JoinColumn(name = "IDBOUTIQUE", referencedColumnName = "idBoutique"),
            inverseJoinColumns = @JoinColumn(name = "IDFAVORIES", referencedColumnName = "idFavories")
    )
    private List<Favories> favories;


    @Override
    public String toString() {
        return "Boutique{" +
                "idBoutique=" + idBoutique +
                ", utilisateur=" + utilisateur +
                ", typeBoutique=" + typeBoutique +
                ", nom='" + nom + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", slogan='" + slogan + '\'' +
                ", logo='" + logo + '\'' +
                ", numBoutique='" + immatriculationRccm + '\'' +
                '}';
    }
}
