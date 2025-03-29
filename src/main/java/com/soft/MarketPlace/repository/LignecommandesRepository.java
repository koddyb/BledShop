package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.LigneCommandes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LignecommandesRepository extends JpaRepository<LigneCommandes, Integer>{

    LigneCommandes findByCommande(Commande commande);

}