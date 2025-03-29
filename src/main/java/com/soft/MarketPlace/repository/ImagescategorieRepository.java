package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.ImageCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImagescategorieRepository extends JpaRepository<ImageCategorie, Integer>{

    List<ImageCategorie> findByNom(String nom);
}