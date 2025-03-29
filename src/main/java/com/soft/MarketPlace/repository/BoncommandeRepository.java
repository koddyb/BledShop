package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.BonCommande;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BoncommandeRepository extends JpaRepository<BonCommande, Integer>{

    List<BonCommande> findByCommande(Commande commande);

    List<BonCommande> findByClient(Client client);

    BonCommande findByCode(String code);
}