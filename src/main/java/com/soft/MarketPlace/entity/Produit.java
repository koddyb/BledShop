package com.soft.MarketPlace.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRODUIT")
    private int idProduit;
    @ManyToOne
    @JoinColumn(name = "IDUTILISATEUR")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "IDCATEGORIE")
    private Categorie categorie;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "QUANTITE")
    private int quantite;
    @Column(name = "PRIX")
    private float prix;
    @Column(name = "REFERENCE")
    private String reference;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DATEAJOUT")
    private Date dateAjout;
    @Column(name = "SEUIL")
    private int seuil;

    //relation
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Image> images;
    @OneToMany(mappedBy = "produit")
    private List<LigneCommandes> ligneCommandes;
    @OneToMany(mappedBy = "produit")
    private List<LignePaniers> lignePaniers;
    @ManyToOne
    @JoinColumn(name="IDTYPEPRODUIT")
    private TypeProduit typeProduit;

    //relation n a n entre les produits et les couleurs
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "couleurproduit",
            joinColumns = @JoinColumn(
                    name = "IDPRODUIT", referencedColumnName = "idProduit"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "IDCOULEUR", referencedColumnName = "idCouleur"
            )
    )
    private List<Couleurs> couleurs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tailleproduit",
            joinColumns = @JoinColumn(name = "IDPRODUIT", referencedColumnName = "idProduit"),
            inverseJoinColumns = @JoinColumn(name = "IDTAILLE", referencedColumnName = "idTaille")
    )
    private List<Taille> tailles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="produitfavorie",
            joinColumns = @JoinColumn(name = "IDPRODUIT", referencedColumnName = "idProduit"),
            inverseJoinColumns = @JoinColumn(name = "IDFAVORIES", referencedColumnName = "idFavories")
    )
    private List<Favories> favories;

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", utilisateur=" + utilisateur +
                ", categorie=" + categorie +
                ", libelle='" + libelle + '\'' +
                ", quantite='" + quantite + '\'' +
                ", prix='" + prix + '\'' +
                ", reference='" + reference + '\'' +
                ", description='" + description + '\'' +
                ", dateAjout=" + dateAjout +
                ", seuil=" + seuil +
                ", images=" + images +
                ", ligneCommandes=" + ligneCommandes +
                ", lignePaniers=" + lignePaniers +
                ", favories=" + favories +
                ", typeProduit=" + typeProduit +
                ", couleurs=" + couleurs +
                ", tailles=" + tailles +
                '}';
    }
}
