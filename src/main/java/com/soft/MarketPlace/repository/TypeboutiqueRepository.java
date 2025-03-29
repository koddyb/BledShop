package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Categorie;
import com.soft.MarketPlace.entity.TypeBoutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.lang.reflect.Type;
import java.util.List;

public interface TypeboutiqueRepository extends JpaRepository<TypeBoutique, Integer>{

    TypeBoutique findByLibelle(String libelle);

}