package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;

import java.util.List;

public interface BonCommandeService {

    //toutes les methodes qui concernent le bon de commande

    public List<BonCommande> getAllBonCommande();
    public BonCommande getBonCommandeById(int id);
    public BonCommande addBonCommande(BonCommande bonCommande);
    public BonCommande updateBonCommande(BonCommande bonCommande);
    public void deleteBonCommande(int id);
    public List<BonCommande> getBonCommandeByCommande(Commande commande);
    public List<BonCommande> getBonCommandeByClient(Client client);

    public BonCommande getBonCommandeByCode(String code);
}
