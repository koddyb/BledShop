package com.soft.MarketPlace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "couleurs")
public class Couleurs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCOULEUR")
    private int idCouleurs;
    @Column(name = "NOM")
    private String nom;

    @Override
    public String toString() {
        return "Couleurs{" +
                "idCouleurs=" + idCouleurs +
                ", nom='" + nom + '\'' +
                '}';
    }
}