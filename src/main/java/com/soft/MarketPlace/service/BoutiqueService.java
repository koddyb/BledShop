package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoutiqueService {

    //toutes les methodes qui concernent la boutique

    public Page<Boutique> getAllBoutique(int pageNo);
    public Boutique getBoutiqueById(int id);
    public Page<Boutique> getBoutiqueByNom(String nom, int pageNo);
    public Boutique addBoutique(Boutique boutique);
    public Boutique updateBoutique(Boutique boutique);
    public void deleteBoutique(int id);
    public List<Boutique> getBoutiqueByUtilisateur(Utilisateur utilisateur);
    public Page<Boutique> getBoutiqueByTypeBoutique(TypeBoutique typeBoutique, int pageNo);
    public Page<Boutique> getBoutiqueByEmplacement(String emplacement, int pageNo);

    public List<Boutique> getByTypeBoutique(TypeBoutique typeBoutique);

    public Boutique getBynom(String nomBoutique);

    public Boutique getBoutiqueByImmatriculation(String immatriculation);

    public Page<Boutique> getBoutiqueByFavorite(int pageNo);

    public Page<Boutique> getBoutiqueByTagName(String name,int pageNo);

}
