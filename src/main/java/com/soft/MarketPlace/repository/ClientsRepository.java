package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientsRepository extends JpaRepository<Client, Integer>{

    List<Client> findByNom(String nom);

    List<Client> findByPrenom(String prenom);

    Client findByEmail(String email);
}