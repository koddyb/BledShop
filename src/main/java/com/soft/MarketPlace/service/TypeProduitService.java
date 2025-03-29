package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.TypeProduit;

import java.util.List;

public interface TypeProduitService {
    //toutes les methodes qui concernent le type de produit

    public List<TypeProduit> getAllTypeProduit();
    public TypeProduit getTypeProduitById(int id);
    public TypeProduit getTypeProduitByNom(String nom);
    public TypeProduit addTypeProduit(TypeProduit typeProduit);
    public TypeProduit updateTypeProduit(TypeProduit typeProduit);
    public void deleteTypeProduit(int id);
    public List<TypeProduit> getTypeProduitByCategorie(Categorie categorie);
}
