package com.soft.MarketPlace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favories")
public class Favories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFAVORIES")
    private int idFavories;
    @Column(name = "NOMBRES")
    private int nombres;
    @ManyToOne
    @JoinColumn(name="IDCLIENT")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="produitfavorie",
            joinColumns = @JoinColumn(name = "IDFAVORIES", referencedColumnName = "idFavories"),
            inverseJoinColumns = @JoinColumn(name = "IDPRODUIT", referencedColumnName = "idProduit")
    )
    private List<Produit> produit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="boutiquefavorie",
            joinColumns = @JoinColumn(name = "IDFAVORIES", referencedColumnName = "idFavories"),
            inverseJoinColumns = @JoinColumn(name = "IDBOUTIQUE", referencedColumnName = "idBoutique")
    )
    private List<Boutique> boutiques;
    @Override
    public String toString() {
        return "Favories{" +
                "idFavories=" + idFavories +
                ", nombres=" + nombres +
                ", client=" + client +
                ", produit=" + produit +
                ", boutique=" + boutiques +
                '}';
    }
}
