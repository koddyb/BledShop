package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Categorie;

import java.util.List;

public interface CategorieService {

    //toutes les methodes qui concernent la categorie

    public List<Categorie> getAllCategorie();
    public Categorie getCategorieById(int id);
    public Categorie getCategorieByLibelle(String libelle);
    public Categorie addCategorie(Categorie categorie);
    public Categorie updateCategorie(Categorie categorie);
    public void deleteCategorie(int id);

    public List<Categorie> getCategorieByBoutiques(Boutique boutique);
}
