package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;

import java.util.List;

public interface TailleService {
    //toutes les methodes qui concernent la taille

    public List<Taille> getAllTaille();
    public Taille getTailleById(int id);
    public Taille getTailleByLibelle(String libelle);
    public Taille addTaille(Taille taille);
    public Taille updateTaille(Taille taille);
    public void deleteTaille(int id);
    public List<Taille> getAllTailleByCategorie(Categorie categorie);

    //public List<Taille> getTailleByProduit(Produit produit);
}
