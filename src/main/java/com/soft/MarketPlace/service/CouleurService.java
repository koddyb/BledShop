package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Couleurs;
import com.soft.MarketPlace.entity.Taille;

import java.util.List;

public interface CouleurService {

    //toutes les methodes qui concernent la couleur

    public List<Couleurs> getAllCouleur();
    public Couleurs getCouleursById(int id);
    public Couleurs getCouleurByNom(String nom);
    public Couleurs addCouleur(Couleurs couleur);
    public Couleurs updateCouleur(Couleurs couleur);
    public void deleteCouleur(int id);
    //public List<Couleurs> getCouleursByProduit(Produit produit);
}
