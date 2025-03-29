package com.soft.MarketPlace.entity;

import java.util.Date;

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
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLIVRAISON")
    private int idLivraison;
    @OneToOne
    @JoinColumn(name = "IDCOMMANDE")
    private Commande commande;
    @Column(name = "DATELIVRAISON")
    private Date dateLivraison;

    @Column(name = "ETAT")
    private Boolean etat;
    @Column(name = "LIEU")
    private String lieu;

    @Override
    public String toString() {
        return "Livraison{" +
                "idLivraison=" + idLivraison +
                ", commande=" + commande +
                ", dateLivraison=" + dateLivraison +
                ", etat=" + etat +
                ", lieu='" + lieu + '\'' +
                '}';
    }
}
