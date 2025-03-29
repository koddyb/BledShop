package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.LigneCommandes;
import com.soft.MarketPlace.repository.LignecommandesRepository;
import com.soft.MarketPlace.service.LigneCommandesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LignecommandesServiceImplement implements LigneCommandesService {

    @Autowired
    private LignecommandesRepository lignecommandesRepository;

    public LignecommandesServiceImplement(LignecommandesRepository lignecommandesRepository) {
        this.lignecommandesRepository = lignecommandesRepository;
    }

    @Override
    public List<LigneCommandes> getAllLigneCommandes() {
        return lignecommandesRepository.findAll();
    }

    @Override
    public LigneCommandes getLigneCommandesById(int id) {
        return lignecommandesRepository.findById(id).get();
    }

    @Override
    public LigneCommandes addLigneCommandes(LigneCommandes ligneCommandes) {
        return lignecommandesRepository.save(ligneCommandes);
    }

    @Override
    public LigneCommandes updateLigneCommandes(LigneCommandes ligneCommandes) {
        return lignecommandesRepository.save(ligneCommandes);
    }

    @Override
    public void deleteLigneCommandes(int id) {
        lignecommandesRepository.deleteById(id);
    }

    @Override
    public LigneCommandes getLigneCommandeByCommande(Commande commande) {
        return lignecommandesRepository.findByCommande(commande);
    }
}
