package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.FavorieRepository;
import com.soft.MarketPlace.service.FavorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavorieServiceImplement implements FavorieService {

    @Autowired
    private FavorieRepository favorieRepository;

    @Override
    public List<Favories> getAllFavories() {
        return favorieRepository.findAll();
    }

    @Override
    public Favories getFavoriesById(int id) {
        return favorieRepository.findById(id).get();
    }

    @Override
    public Favories addFavories(Favories favories) {
        return favorieRepository.save(favories);
    }

    @Override
    public Favories updateFavories(Favories favories) {
        return favorieRepository.save(favories);
    }

    @Override
    public void deleteFavories(int id) {
        favorieRepository.deleteById(id);
    }

    @Override
    public List<Favories> getFavoriesByClient(Client client) {
        return favorieRepository.findByClient(client);
    }

    @Override
    public List<Favories> getFavoriesByBoutiques(Boutique boutique) {
        return favorieRepository.findByBoutiques(boutique);
    }

    @Override
    public List<Favories> getFavoriesByProduit(Produit produit) {
        return favorieRepository.findByProduit(produit);
    }
}
