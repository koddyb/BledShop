package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Couleurs;
import com.soft.MarketPlace.repository.CouleurRepository;
import com.soft.MarketPlace.service.CouleurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouleurServiceImplement implements CouleurService {

    @Autowired
    CouleurRepository couleurRepository;

    @Override
    public List<Couleurs> getAllCouleur() {
        return couleurRepository.findAll();
    }

    @Override
    public Couleurs getCouleursById(int id) {
        return couleurRepository.findById(id).get();
    }

    @Override
    public Couleurs getCouleurByNom(String nom) {
        return couleurRepository.findByNom(nom);
    }

    @Override
    public Couleurs addCouleur(Couleurs couleur) {
        return couleurRepository.save(couleur);
    }

    @Override
    public Couleurs updateCouleur(Couleurs couleur) {
        return couleurRepository.save(couleur);
    }

    @Override
    public void deleteCouleur(int id) {
        couleurRepository.deleteById(id);
    }
}
