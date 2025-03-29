package com.soft.MarketPlace.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "typeproduit")
public class TypeProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTYPEPRODUIT")
    private int idTypeProduit;
    @Column(name = "NOM")
    private String nom;
    
    @ManyToOne
    @JoinColumn(name = "IDCATEGORIE")
    private Categorie categorie;
    
    @OneToMany(mappedBy = "typeProduit")
    private List<Produit> produits;

    @Override
    public String toString() {
        return "TypeProduit{" +
                "idTypeProduit=" + idTypeProduit +
                ", nom='" + nom + '\'' +
                ", categorie=" + categorie +
                ", produits=" + produits +
                '}';
    }
}
