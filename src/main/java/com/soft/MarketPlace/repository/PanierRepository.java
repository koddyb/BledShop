package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanierRepository extends JpaRepository<Panier, Integer>{

    Panier findByDatePanier(String date);

    Panier findByClient(Client client);
}