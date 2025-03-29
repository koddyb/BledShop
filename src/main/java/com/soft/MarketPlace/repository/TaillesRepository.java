package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.Produit;
import com.soft.MarketPlace.entity.Taille;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaillesRepository extends JpaRepository<Taille, Integer>{

    Taille findByLibelle(String libelle);

    List<Taille> findByCategorie(Categorie categorie);

    //List<Taille> findByProduit(Produit produit);
}