package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Produit;
import com.soft.MarketPlace.entity.Taille;
import com.soft.MarketPlace.entity.TypeBoutique;

import java.util.List;

public interface TypeBoutiqueService {
    //toutes les methodes qui concernent le type de boutique

    public List<TypeBoutique> getAllTypeBoutique();
    public TypeBoutique getTypeBoutiqueById(int id);
    public TypeBoutique getTypeBoutiqueByLibelle(String libelle);
    public TypeBoutique addTypeBoutique(TypeBoutique typeBoutique);
    public TypeBoutique updateTypeBoutique(TypeBoutique typeBoutique);
    public void deleteTypeBoutique(int id);
}
