package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.TypeBoutique;
import com.soft.MarketPlace.entity.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeProduitRepository extends JpaRepository<TypeProduit, Integer> {
    TypeProduit findByNom(String nom);
    List<TypeProduit> findByCategorie(Categorie categorie);

}
