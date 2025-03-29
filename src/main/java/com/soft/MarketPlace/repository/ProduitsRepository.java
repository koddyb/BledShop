package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProduitsRepository extends JpaRepository<Produit, Integer>{

    Produit findByLibelle(String libelle);

    Page<Produit> findByUtilisateur(Utilisateur utilisateur, Pageable pageable);

    Page<Produit> findByCategorie(Categorie categorie, Pageable pageable);

    Page<Produit> findByCouleurs(Couleurs couleurs, Pageable pageable);

    Page<Produit> findByTailles(Taille taille, Pageable pageable);

    Page<Produit> findByTypeProduit(TypeProduit typeProduit, Pageable pageable);

    List<Produit> findByTypeProduit(TypeProduit typeProduit);

    List<Produit> findByUtilisateur(Utilisateur utilisateur);

    //pour la pagination

    @Query("select p from Produit p")
    Page<Produit> pageProduit(Pageable pageable);



}