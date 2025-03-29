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
@Table(name = "panier")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPANIER")
    private int idPanier;
    @ManyToOne
    @JoinColumn(name = "IDCLIENT")
    private Client client;
    @Column(name = "DATEPANIER")
    private Date datePanier;
    @Column(name = "VALIDE")
    private boolean valide;
    @Column(name = "ABOUTI")
    private boolean abouti;

    @Column(name = "PRIXTOTAL")
    private float prixTotal;

    //relations
    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<LignePaniers> lignePaniers;

    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + idPanier +
                ", client=" + client +
                ", datePanier=" + datePanier +
                ", valide=" + valide +
                ", abouti=" + abouti +
                ", lignePaniers=" + lignePaniers +
                '}';
    }
}
