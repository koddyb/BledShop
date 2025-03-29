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
@Table(name = "boncommande")
public class BonCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDBONCOMMANDE")
    private int idBonCommande;
    @OneToOne
    @JoinColumn(name = "IDCOMMANDE")
    private Commande commande;
    @ManyToOne
    @JoinColumn(name = "IDCLIENT")
    private Client client;
    @Column(name = "CODE")
    private String code;

    @Override
    public String toString() {
        return "BonCommande{" +
                "idBonCommande=" + idBonCommande +
                ", commande=" + commande +
                ", client=" + client +
                ", code='" + code + '\'' +
                '}';
    }
}
