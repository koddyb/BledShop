package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.repository.CategoriesRepository;
import com.soft.MarketPlace.service.CategorieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImplement implements CategorieService {

    private CategoriesRepository categoriesRepository;

    public CategoriesServiceImplement(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Categorie> getAllCategorie() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categorie getCategorieById(int id) {
        return categoriesRepository.findById(id).get();
    }

    @Override
    public Categorie getCategorieByLibelle(String libelle) {
        return categoriesRepository.findByLibelle(libelle);
    }

    @Override
    public Categorie addCategorie(Categorie categorie) {
        return categoriesRepository.save(categorie);
    }

    @Override
    public Categorie updateCategorie(Categorie categorie) {
        return categoriesRepository.save(categorie);
    }

    @Override
    public void deleteCategorie(int id) {
        categoriesRepository.deleteById(id);
    }

    @Override
    public List<Categorie> getCategorieByBoutiques(Boutique boutique) {
        return categoriesRepository.findByBoutiques(boutique);
    }
}
