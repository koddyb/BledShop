package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categorie, Integer>{
    Categorie findByLibelle(String libelle);

    List<Categorie> findByBoutiques(Boutique boutique);
}