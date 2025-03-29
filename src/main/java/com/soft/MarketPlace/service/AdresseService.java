package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.User;
import com.soft.MarketPlace.entity.Utilisateur;

import java.util.List;

public interface AdresseService {

    //toutes les methodes qui concernent l'adresse

    public List<Adresse> getAllAdresse();
    public Adresse getAdresseById(int id);
    public Adresse addAdresse(Adresse adresse);
    public Adresse updateAdresse(Adresse adresse);
    public void deleteAdresse(int id);

    public List<Adresse> getAdresseByUser(User user);
}
