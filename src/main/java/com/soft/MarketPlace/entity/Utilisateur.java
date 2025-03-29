package com.soft.MarketPlace.entity;

import java.util.Date;
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
@Table(name = "utilisateurs")
public class Utilisateur extends User{

    @Column(name = "NUMEROCNI", nullable = false)
    private String numeroCni;

    @Column(name="CHIFFREAFFAIRE", nullable = false)
    private float chiffreAffaire;
    //relations
    @OneToMany(mappedBy = "utilisateur")
    private List<Produit> produits;

    @OneToMany(mappedBy = "utilisateur")
    private List<Commande> commandes;

    @OneToOne(mappedBy = "utilisateur")
    private Boutique boutique;

    @Override
    public String toString() {
        return "Utilisateur{" +
                "numeroCni='" + numeroCni + '\'' +
                ", produits=" + produits +
                ", commandes=" + commandes +
                ", boutique=" + boutique +
                '}';
    }
}
