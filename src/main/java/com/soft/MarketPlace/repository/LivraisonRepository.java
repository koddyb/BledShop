package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LivraisonRepository extends JpaRepository<Livraison, Integer>{

    Livraison findByDateLivraison(String date);

    List<Livraison> findByCommande(Commande commande);
}