package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Utilisateur;
import com.soft.MarketPlace.repository.UtilisateursRepository;
import com.soft.MarketPlace.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateursServiceImplement implements UtilisateurService {

    @Autowired
    private UtilisateursRepository utilisateursRepository;

    @Override
    public List<Utilisateur> getAllUtilisateur() {
        return utilisateursRepository.findAll();
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        return utilisateursRepository.findById(id).get();
    }

    @Override
    public List<Utilisateur> getUtilisateurByNom(String nom) {
        return utilisateursRepository.findByNom(nom);
    }

    @Override
    public List<Utilisateur> getUtilisateurByPrenom(String prenom) {
        return utilisateursRepository.findByPrenom(prenom);
    }

    @Override
    public Utilisateur getUtilisateurByNumeroCni(String numeroCni) {
        return utilisateursRepository.findByNumeroCni(numeroCni);
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateursRepository.findByEmail(email);
    }

    @Override
    public Utilisateur getUtilisateurByBoutique(Boutique boutique) {
        return utilisateursRepository.findByBoutique(boutique);
    }

    @Override
    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(utilisateur.getPassword());
        return utilisateursRepository.save(utilisateur);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateursRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(int id) {
        utilisateursRepository.deleteById(id);
    }

}
