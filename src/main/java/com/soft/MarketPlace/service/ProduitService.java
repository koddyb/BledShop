package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProduitService {

    //toutes les methodes qui concernent le produit

    public List<Produit> getAllProduit();
    public Produit getProduitById(int id);
    public Produit getProduitByLibelle(String nom);
    public Produit addProduit(Produit produit);
    public Produit updateProduit(Produit produit);
    public void deleteProduit(int id);
    public Page<Produit> getProduitByUtilisateur(Utilisateur utilisateur, int pageNo);

    public List<Produit> getProduitByCriteria(Utilisateur utilisateur, List<String> tailles, List<String> couleurs, List<String> typeProduits);

    public Page<Produit> getProduitByCategorie(Categorie categorie, int pageNo);

    public Page<Produit> getProduitByTypeProduit(TypeProduit typeProduit, int pageNo);

    public Page<Produit> getProduitBySold(int pageNo);

    public Page<Produit> getProduitByFavorite(int pageNo);

    public List<Produit> getByTypeProduit(TypeProduit typeProduit);

    public Page<Produit> getProduitByCouleurs(Couleurs couleurs, int pageNo);

    public Page<Produit> getProduitByTaille(Taille taille, int pageNo);

    public Page<Produit> pageProduit(int pageNo);

    Page<Produit> getProduitByFavoriteClient(int pageNo, Client client);

    List<Produit> getByUtilisateur(Utilisateur utilisateur);


    Page<Produit> getProduitByTagName(String libelle, int pageNo);

    public Page<Produit> getProduitBoutiqueByTagName(int idBoutique,String name,int pageNo);

}
