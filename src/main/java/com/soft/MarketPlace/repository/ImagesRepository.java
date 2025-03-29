package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Image, Integer>{

    List<Image> findByNom(String nom);
}