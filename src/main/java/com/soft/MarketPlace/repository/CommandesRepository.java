package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandesRepository extends JpaRepository<Commande, Integer>{

    Commande findByDateCommande(String date);

    List<Commande> findByEtat(String etat);

    List<Commande> findByUtilisateur(Utilisateur utilisateur);

    List<Commande> findByClient(Client client);
}