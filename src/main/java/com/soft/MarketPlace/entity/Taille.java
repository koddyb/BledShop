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
@Table(name = "tailles")
public class Taille {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTAILLE")
    private int idTaille;
    @Column(name = "LIBELLE")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "IDCATEGORIE")
    private Categorie categorie;

    @Override
    public String toString() {
        return "Taille{" +
                "idTaille=" + idTaille +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
