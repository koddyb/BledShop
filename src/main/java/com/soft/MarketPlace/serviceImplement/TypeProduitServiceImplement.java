package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.TypeProduit;
import com.soft.MarketPlace.repository.TypeProduitRepository;
import com.soft.MarketPlace.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TypeProduitServiceImplement implements TypeProduitService {

    @Autowired
    TypeProduitRepository typeProduitRepository;

    @Override
    public List<TypeProduit> getAllTypeProduit() {
        return typeProduitRepository.findAll();
    }

    @Override
    public TypeProduit getTypeProduitById(int id) {
        return typeProduitRepository.findById(id).get();
    }

    @Override
    public TypeProduit getTypeProduitByNom(String nom) {
        return typeProduitRepository.findByNom(nom);
    }

    @Override
    public TypeProduit addTypeProduit(TypeProduit typeProduit) {
        return typeProduitRepository.save(typeProduit);
    }

    @Override
    public TypeProduit updateTypeProduit(TypeProduit typeProduit) {
        return typeProduitRepository.save(typeProduit);
    }

    @Override
    public void deleteTypeProduit(int id) {
        typeProduitRepository.deleteById(id);
    }

    @Override
    public List<TypeProduit> getTypeProduitByCategorie(Categorie categorie) {
        return typeProduitRepository.findByCategorie(categorie);
    }
}
