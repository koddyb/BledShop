package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Couleurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouleurRepository extends JpaRepository<Couleurs, Integer> {
    Couleurs findByNom(String nom);
}
