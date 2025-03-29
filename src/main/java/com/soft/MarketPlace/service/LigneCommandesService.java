package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.LigneCommandes;
import com.soft.MarketPlace.entity.Utilisateur;

import java.util.List;

public interface LigneCommandesService {

    //toutes les methodes qui concernent la ligneCommande

    public List<LigneCommandes> getAllLigneCommandes();
    public LigneCommandes getLigneCommandesById(int id);
    public LigneCommandes addLigneCommandes(LigneCommandes ligneCommandes);
    public LigneCommandes updateLigneCommandes(LigneCommandes ligneCommandes);
    public void deleteLigneCommandes(int id);

    public LigneCommandes getLigneCommandeByCommande(Commande commande);
}
