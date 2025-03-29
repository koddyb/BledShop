package com.soft.MarketPlace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "lignepaniers")
public class LignePaniers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLIGNEPANIER")
    private int idLignePanier;
    @ManyToOne
    @JoinColumn(name = "IDPANIER")
    private Panier panier;
    @OneToOne
    @JoinColumn(name = "IDPRODUIT")
    private Produit produit;
    @Column(name = "QUANTITE")
    private int quantite;
    @Column(name = "PRIX")
    private float prix;

    @Column(name = "COULEUR")
    private String couleur;

    @Column(name = "TAILLE")
    private String taille;

    @Override
    public String toString() {
        return "LignePaniers{" +
                "idLignePanier=" + idLignePanier +
                ", panier=" + panier +
                ", produit=" + produit +
                ", quantite=" + quantite +
                ", prix=" + prix +
                '}';
    }
}
