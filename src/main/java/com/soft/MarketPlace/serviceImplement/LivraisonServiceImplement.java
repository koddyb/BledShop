package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Livraison;
import com.soft.MarketPlace.repository.LivraisonRepository;
import com.soft.MarketPlace.service.LivraisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivraisonServiceImplement implements LivraisonService {

    @Autowired
    private LivraisonRepository livraisonRepository;

    public LivraisonServiceImplement(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    @Override
    public List<Livraison> getAllLivraison() {
        return livraisonRepository.findAll();
    }

    @Override
    public Livraison getLivraisonById(int id) {
        return livraisonRepository.findById(id).get();
    }

    @Override
    public Livraison getLivraisonByDateLivraison(String date) {
        return livraisonRepository.findByDateLivraison(date);
    }

    @Override
    public Livraison addLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public Livraison updateLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    @Override
    public void deleteLivraison(int id) {
        livraisonRepository.deleteById(id);
    }

    @Override
    public List<Livraison> getLivraisonByCommande(Commande commande) {
        return livraisonRepository.findByCommande(commande);
    }
}
