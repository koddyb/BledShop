package com.soft.MarketPlace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "typeboutique")
public class TypeBoutique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTYPEBOUTIQUE")
    private int idTypeBoutique;
    @Column(name = "LIBELLE")
    private String libelle;
    @Column(name = "LIVRABLE")
    private boolean livrable;

    //relation
    @OneToMany(mappedBy = "typeBoutique")
    private List<Boutique> boutiques;

    @Override
    public String toString() {
        return "TypeBoutique{" +
                "idTypeBoutique=" + idTypeBoutique +
                ", libelle='" + libelle + '\'' +
                ", livrable=" + livrable +
                ", boutiques=" + boutiques +
                '}';
    }
}
