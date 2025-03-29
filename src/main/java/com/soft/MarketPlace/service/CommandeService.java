package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;
import jakarta.mail.MessagingException;

import java.util.List;

public interface CommandeService {

    //toutes les methodes qui concernent la commande

    public List<Commande> getAllCommande();
    public Commande getCommandeById(int id);
    public Commande getCommandeByDateCommande(String date);
    public List<Commande> getCommandeByEtat(Client client, String etat);

    public List<Commande> getCommandeByEtats(String etat);
    public List<Commande> getCommandeUtilisateurByEtat(Utilisateur utilisateur, String etat);
    public Commande addCommande(Panier panier, String lieux, String moyenPaiement);
    public Commande updateCommande(Commande commande);
    public void deleteCommande(int id);
    public List<Commande> getCommandeByUtilisateur(Utilisateur utilisateur);
    public List<Commande> getCommandeByClient(Client client);

    public Commande validerCommande(Commande commande, String code, Utilisateur utilisateur, Client client);
}
