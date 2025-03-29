package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.TypeBoutique;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BoutiquesRepository extends JpaRepository<Boutique, Integer>{

    Page<Boutique> findByNom(String nom, Pageable pageable);

    Boutique findByNom(String nom);

    List<Boutique> findByUtilisateur(Utilisateur utilisateur);

    Page<Boutique> findByTypeBoutique(TypeBoutique typeBoutique, Pageable pageable);

    List<Boutique> findByTypeBoutique(TypeBoutique typeBoutique);

    Page<Boutique> findByEmplacement(String emplacement, Pageable pageable);

    Boutique findByImmatriculationRccm(String immatriculationRccm);
}