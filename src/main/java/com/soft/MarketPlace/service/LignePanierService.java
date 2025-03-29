package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.LignePaniers;

import java.util.List;

public interface LignePanierService {

    //toutes les methodes qui concernent la lignePanier

    public List<LignePaniers> getAllLignePaniers();
    public LignePaniers getLignePaniersById(int id);
    public LignePaniers addLignePaniers(LignePaniers lignePaniers);
    public LignePaniers updateLignePaniers(LignePaniers lignePaniers);
    public void deleteLignePaniers(int id);
}
