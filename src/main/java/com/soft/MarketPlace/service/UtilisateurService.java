package com.soft.MarketPlace.service;


import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface UtilisateurService{
    //toutes les methodes qui concernent l'utilisateur'

    public List<Utilisateur> getAllUtilisateur();
    public Utilisateur getUtilisateurById(int id);
    public List<Utilisateur> getUtilisateurByNom(String nom);
    public List<Utilisateur> getUtilisateurByPrenom(String prenom);
    public Utilisateur getUtilisateurByNumeroCni(String numeroCni);
    public Utilisateur getUtilisateurByEmail(String email);

    public Utilisateur getUtilisateurByBoutique(Boutique boutique);
    public Utilisateur addUtilisateur(Utilisateur utilisateur);
    public Utilisateur updateUtilisateur(Utilisateur utilisateur);
    public void deleteUtilisateur(int id);
}
