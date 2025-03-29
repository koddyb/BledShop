package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.BoutiquesRepository;
import com.soft.MarketPlace.service.BoutiqueService;
import com.soft.MarketPlace.service.FavorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BoutiquesServiceImplement implements BoutiqueService {

    private BoutiquesRepository boutiquesRepository;

    @Autowired
    private FavorieService favorieService;

    public BoutiquesServiceImplement(BoutiquesRepository boutiquesRepository) {
        this.boutiquesRepository = boutiquesRepository;
    }

    @Override
    public Page<Boutique> getAllBoutique(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 20);
        return boutiquesRepository.findAll(pageable);
    }

    @Override
    public Boutique getBoutiqueById(int id) {
        return boutiquesRepository.findById(id).get();
    }

    @Override
    public Page<Boutique> getBoutiqueByNom(String nom, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 20);
        return boutiquesRepository.findByNom(nom, pageable);
    }

    @Override
    public Boutique addBoutique(Boutique boutique) {
        return boutiquesRepository.save(boutique);
    }

    @Override
    public Boutique updateBoutique(Boutique boutique) {
        return boutiquesRepository.save(boutique);
    }

    @Override
    public void deleteBoutique(int id) {
        boutiquesRepository.deleteById(id);
    }

    @Override
    public List<Boutique> getBoutiqueByUtilisateur(Utilisateur utilisateur) {
        return boutiquesRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public Page<Boutique> getBoutiqueByTypeBoutique(TypeBoutique typeBoutique, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 20);
        return boutiquesRepository.findByTypeBoutique(typeBoutique, pageable);
    }

    @Override
    public Page<Boutique> getBoutiqueByEmplacement(String emplacement, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 20);
        return boutiquesRepository.findByEmplacement(emplacement, pageable);
    }

    @Override
    public List<Boutique> getByTypeBoutique(TypeBoutique typeBoutique) {
        return boutiquesRepository.findByTypeBoutique(typeBoutique);
    }

    @Override
    public Boutique getBynom(String nomBoutique) {
        return boutiquesRepository.findByNom(nomBoutique);
    }

    @Override
    public Boutique getBoutiqueByImmatriculation(String immatriculation) {
        return boutiquesRepository.findByImmatriculationRccm(immatriculation);
    }

    //pour extraire les boutiques les mieux notées
    @Override
    public Page<Boutique> getBoutiqueByFavorite(int pageNo) {
        //un dictionnaire qui portera chaque produit et son nombre de favories
        Map<String,Integer> dictionnaireDesFavories = new HashMap<>();

        Pageable pageable = PageRequest.of(pageNo, 25);

        List<Boutique> boutiques = new ArrayList<>();

        List<Favories> favories = favorieService.getAllFavories();
        if(favories == null){
            return null;
        }

        for (Favories f: favories) {
            //pour chaque favorie, je compte les produits pour les marqués comme plus notés
            List<Boutique> boutiqueFavories = f.getBoutiques();
            for(Boutique b : boutiqueFavories){
                dictionnaireDesFavories.putIfAbsent(b.getNom(),1);

                if(dictionnaireDesFavories.containsKey(b.getNom())){
                    //je récupère le nombre de fois pour mettre à jour
                    int nombre = dictionnaireDesFavories.get(b.getNom());
                    nombre += 1;
                    dictionnaireDesFavories.replace(b.getNom(), nombre);
                }
            }
        }

        if(dictionnaireDesFavories == null){
            return null;
        }

        //convertir le dictionnaire en une liste d'entrer
        List<Map.Entry<String, Integer>> listeMaps = new ArrayList<>(dictionnaireDesFavories.entrySet());

        //variable ou comparateur pour trier les dictionnaires
        Comparator<Map.Entry<String, Integer>> comparateurDentrer = new Comparator<Map.Entry<String, Integer>>(){
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        };

        //on tri notre map
        Collections.sort(listeMaps, comparateurDentrer);

        //on doit parcourir le dictionnaire pour récupérer ces produits et les ajouter à la liste par priorité
        for(Map.Entry<String, Integer> dictionnaire : listeMaps){
            Boutique boutique = boutiquesRepository.findByNom(dictionnaire.getKey());
            boutiques.add(boutique);
        }

        if(boutiques == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, boutiques.size());

        // Récupérez la liste de produits pour la page
        List<Boutique> contenuPage = boutiques.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Boutique> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), boutiques.size());

        // Retournez la page de produits
        return page;
    }

    @Override
    public Page<Boutique> getBoutiqueByTagName(String name, int pageNo) {
        List<Boutique> allBoutiques = boutiquesRepository.findAll();
        List<Boutique> boutiques = new ArrayList<>();

        if(allBoutiques == null){
            return null;
        }

        for(Boutique b: allBoutiques){
            if(b.getNom().contains(name.trim()) || b.getNom().toUpperCase().contains(name.trim())){
                boutiques.add(b);
            }
        }

        if(boutiques == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, boutiques.size());

        // Récupérez la liste de produits pour la page
        List<Boutique> contenuPage = boutiques.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Boutique> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), boutiques.size());

        // Retournez la page de boutiques
        return page;
    }

}
