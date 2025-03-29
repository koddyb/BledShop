package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UtilisateursRepository extends JpaRepository<Utilisateur, Integer>{

    List<Utilisateur> findByNom(String nom);

    Utilisateur findByBoutique(Boutique boutique);

    List<Utilisateur> findByPrenom(String prenom);

    Utilisateur findByNumeroCni(String numeroCni);

    Utilisateur findByEmail(String email);
}