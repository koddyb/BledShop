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
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOMMANDE")
    private int idCommande;
    @ManyToOne
    @JoinColumn(name = "IDUTILISATEUR")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "IDCLIENT")
    private Client client;
    @Column(name = "DATECOMMANDE")
    private Date dateCommande;

    @Column(name = "DATEBUTOIRE")
    private Date dateButoire;
    @Column(name = "ETAT")
    private String etat;

    @Column(name = "PRIX")
    private float prix;

    @Column(name = "MOYENPAIEMENT")
    private String moyenPaiement;

    //relations
    @OneToOne(mappedBy = "commande")
    private BonCommande bonCommande;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommandes> ligneCommandes;

    @OneToOne(mappedBy = "commande")
    private Livraison livraison;

    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + idCommande +
                ", utilisateur=" + utilisateur +
                ", client=" + client +
                ", dateCommande=" + dateCommande +
                ", dateButoire=" + dateButoire +
                ", etat=" + etat +
                ", prix=" + prix +
                ", moyenPaiement=" + moyenPaiement +
                ", bonCommande=" + bonCommande +
                ", ligneCommandes=" + ligneCommandes +
                ", livraison=" + livraison +
                '}';
    }
}
