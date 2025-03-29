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
@Table(name = "clients")
public class Client extends User{

    @Column(name = "BUDGET", nullable = false)
    private String budget;

    //relations
    @OneToMany(mappedBy = "client")
    private List<BonCommande> bonCommandeClient;

    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;

    @OneToMany(mappedBy = "client")
    private List<Favories> favories;

    @OneToOne(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Panier panier;

    @Override
    public String toString() {
        return "Client{" +
                "budget='" + budget + '\'' +
                "nom='"+ getNom() + '\'' +
                "prenom='"+ getPrenom() + '\'' +
                "dateNaissance='"+ getDateNaissance() + '\'' +
                "email='"+ getEmail() + '\'' +
                "telephone1='"+ getTelephone1() + '\'' +
                "telephone2='"+ getTelephone2() + '\'' +
                "password='"+ getPassword() + '\'' +
                "confirmPassword='"+ getConfirmPassword() + '\'' +
                "isValide='"+ getIsValide() + '\'' +
                '}';
    }
}
