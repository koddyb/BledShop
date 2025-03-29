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
@Table(name = "imagescategorie")
public class ImageCategorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDIMAGECATEGORIE")
    private int idImageCategorie;
    @ManyToOne
    @JoinColumn(name = "IDCATEGORIE")
    private Categorie categorie;
    @Column(name = "NOM")
    private String nom;
    @Column(name = "CHEMIN")
    private String chemin;

    @Override
    public String toString() {
        return "ImageCategorie{" +
                "idImageCategorie=" + idImageCategorie +
                ", categorie=" + categorie +
                ", nom='" + nom + '\'' +
                ", chemin='" + chemin + '\'' +
                '}';
    }
}
