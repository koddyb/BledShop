package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;

import java.util.List;

public interface FavorieService {
    //toutes les methodes qui concernent les favories

    public List<Favories> getAllFavories();
    public Favories getFavoriesById(int id);
    public Favories addFavories(Favories favories);
    public Favories updateFavories(Favories favories);
    public void deleteFavories(int id);
    public List<Favories> getFavoriesByClient(Client client);

    public List<Favories> getFavoriesByBoutiques(Boutique boutique);
    public List<Favories> getFavoriesByProduit(Produit produit);
}
