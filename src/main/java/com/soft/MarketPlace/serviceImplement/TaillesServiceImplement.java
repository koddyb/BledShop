package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.Produit;
import com.soft.MarketPlace.entity.Taille;
import com.soft.MarketPlace.repository.TaillesRepository;
import com.soft.MarketPlace.service.TailleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaillesServiceImplement implements TailleService {

    @Autowired
    private TaillesRepository taillesRepository;

    public TaillesServiceImplement(TaillesRepository taillesRepository) {
        this.taillesRepository = taillesRepository;
    }

    @Override
    public List<Taille> getAllTaille() {
        return taillesRepository.findAll();
    }

    @Override
    public Taille getTailleById(int id) {
        return taillesRepository.findById(id).get();
    }

    @Override
    public Taille getTailleByLibelle(String libelle) {
        return taillesRepository.findByLibelle(libelle);
    }

    @Override
    public Taille addTaille(Taille taille) {
        return taillesRepository.save(taille);
    }

    @Override
    public Taille updateTaille(Taille taille) {
        return taillesRepository.save(taille);
    }

    @Override
    public void deleteTaille(int id) {
        taillesRepository.deleteById(id);
    }

    @Override
    public List<Taille> getAllTailleByCategorie(Categorie categorie) {
        return taillesRepository.findByCategorie(categorie);
    }

}
