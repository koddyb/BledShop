package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.BonCommande;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.repository.BoncommandeRepository;
import com.soft.MarketPlace.service.BonCommandeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoncommandeServiceImplement implements BonCommandeService {

    private BoncommandeRepository boncommandeRepository;

    public BoncommandeServiceImplement(BoncommandeRepository boncommandeRepository) {
        this.boncommandeRepository = boncommandeRepository;
    }

    @Override
    public List<BonCommande> getAllBonCommande() {
        return boncommandeRepository.findAll();
    }

    @Override
    public BonCommande getBonCommandeById(int id) {
        return boncommandeRepository.findById(id).get();
    }

    @Override
    public BonCommande addBonCommande(BonCommande bonCommande) {
        return boncommandeRepository.save(bonCommande);
    }

    @Override
    public BonCommande updateBonCommande(BonCommande bonCommande) {
        return boncommandeRepository.save(bonCommande);
    }

    @Override
    public void deleteBonCommande(int id) {
        boncommandeRepository.deleteById(id);
    }

    @Override
    public List<BonCommande> getBonCommandeByCommande(Commande commande) {
        return boncommandeRepository.findByCommande(commande);
    }

    @Override
    public List<BonCommande> getBonCommandeByClient(Client client) {
        return boncommandeRepository.findByClient(client);
    }

    @Override
    public BonCommande getBonCommandeByCode(String code) {
        return boncommandeRepository.findByCode(code);
    }
}
