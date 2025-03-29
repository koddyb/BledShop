package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;

import java.util.List;

public interface LivraisonService {

    //toutes les methodes qui concernent la livraison

    public List<Livraison> getAllLivraison();
    public Livraison getLivraisonById(int id);
    public Livraison getLivraisonByDateLivraison(String Date);
    public Livraison addLivraison(Livraison livraison);
    public Livraison updateLivraison(Livraison livraison);
    public void deleteLivraison(int id);
    public List<Livraison> getLivraisonByCommande(Commande commande);
}
