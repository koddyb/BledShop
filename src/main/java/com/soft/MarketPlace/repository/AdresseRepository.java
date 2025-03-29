package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.User;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresseRepository extends JpaRepository<Adresse, Integer>{

    List<Adresse> findByUser(User user);

}