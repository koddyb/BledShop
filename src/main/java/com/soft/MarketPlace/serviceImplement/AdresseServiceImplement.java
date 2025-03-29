package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.User;
import com.soft.MarketPlace.entity.Utilisateur;
import com.soft.MarketPlace.repository.AdresseRepository;
import com.soft.MarketPlace.service.AdresseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseServiceImplement implements AdresseService {
    private AdresseRepository adresseRepository;

    public AdresseServiceImplement(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    @Override
    public List<Adresse> getAllAdresse() {
        return adresseRepository.findAll();
    }

    @Override
    public Adresse getAdresseById(int id) {
        return adresseRepository.findById(id).get();
    }

    @Override
    public Adresse addAdresse(Adresse adresse) {
        return adresseRepository.save(adresse);
    }

    @Override
    public Adresse updateAdresse(Adresse adresse) {
        return adresseRepository.save(adresse);
    }

    @Override
    public void deleteAdresse(int id) {
        adresseRepository.deleteById(id);
    }

    @Override
    public List<Adresse> getAdresseByUser(User user) {
        return adresseRepository.findByUser(user);
    }

}
