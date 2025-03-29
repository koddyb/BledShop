package com.soft.MarketPlace.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "lignecommandes")
public class LigneCommandes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLIGNECOMMANDE")
    private int idLigneCommande;
    @OneToOne
    @JoinColumn(name = "IDCOMMANDE")
    private Commande commande;
    @OneToOne
    @JoinColumn(name = "IDPRODUIT")
    private Produit produit;
    @Column(name = "QUANTITE")
    private int quantite;
    @Column(name = "PRIX")
    private float prix;

    @Column(name = "TAILLE")
    private String taille;

    @Column(name = "COULEUR")
    private String couleur;

    @Override
    public String toString() {
        return "LigneCommandes{" +
                "idLigneCommande=" + idLigneCommande +
                ", commande=" + commande +
                ", produit=" + produit +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}
