package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;

import java.util.List;

public interface PanierService {
    //toutes les methodes qui concernent le panier

    public List<Panier> getAllPanier();
    public Panier getPanierById(int id);
    public Panier getPanierByDate(String Date);
    public Panier addPanier(Panier panier);
    public Panier updateLivraison(Panier panier);
    public void deletePanier(int id);
    public Panier getPanierByClient(Client client);

    public Panier ajouterLignePagnier(Produit produit, int quantite, Client client);

    public Panier modifierLignePanier(Produit produit, int quantite, String couleur, String taille, Client client);

    public Panier supprimerLignePanier(Produit produit, Client client);
}
