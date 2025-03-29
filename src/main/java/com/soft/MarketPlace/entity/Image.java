package com.soft.MarketPlace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDIMAGE")
    private int idImage;
    @ManyToOne
    @JoinColumn(name = "IDPRODUIT")
    private Produit produit;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "CHEMIN")
    private String chemin;

    @Override
    public String toString() {
        return "Image{" +
                "idImage=" + idImage +
                ", produit=" + produit +
                ", nom='" + nom + '\'' +
                ", chemin='" + chemin + '\'' +
                '}';
    }
}
