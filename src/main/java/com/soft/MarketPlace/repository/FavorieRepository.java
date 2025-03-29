package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavorieRepository extends JpaRepository<Favories, Integer> {
    List<Favories> findByClient(Client client);
    List<Favories> findByProduit(Produit produit);

    List<Favories> findByBoutiques(Boutique boutique);

}
