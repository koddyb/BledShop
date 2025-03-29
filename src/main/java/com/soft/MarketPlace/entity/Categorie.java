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
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCATEGORIE")
    private int idCategorie;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "categorieboutique",
            joinColumns = @JoinColumn(name = "IDCATEGORIE", referencedColumnName = "idCategorie"),
            inverseJoinColumns = @JoinColumn(name = "IDBOUTIQUE", referencedColumnName = "idBoutique")
    )
    private List<Boutique> boutiques;

    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "QUANTITE")
    private String quantite;
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE")
    private String image;

    //relation
    @OneToMany(mappedBy = "categorie")
    private List<ImageCategorie> ImageCategories;

    @OneToMany(mappedBy = "categorie")
    private List<Produit> produits;

    @OneToMany(mappedBy = "categorie")
    private List<TypeProduit> typeProduits;

    @OneToMany(mappedBy = "categorie")
    private List<Taille> tailles;

    @Override
    public String toString() {
        return "Categorie{" +
                "idCategorie=" + idCategorie +
                ", boutiques=" + boutiques +
                ", libelle='" + libelle + '\'' +
                ", quantite='" + quantite + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", ImageCategories=" + ImageCategories +
                ", produits=" + produits +
                ", typeProduits=" + typeProduits +
                '}';
    }
}
