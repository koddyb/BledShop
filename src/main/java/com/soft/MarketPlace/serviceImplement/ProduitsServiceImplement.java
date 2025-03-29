package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.ProduitsRepository;
import com.soft.MarketPlace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProduitsServiceImplement implements ProduitService {

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private FavorieService favorieService;

    @Autowired
    ClientService clientService;

    @Autowired
    BoutiqueService boutiqueService;

    @Autowired
    private CommandeService commandeService;

    public ProduitsServiceImplement(ProduitsRepository produitsRepository) {
        this.produitsRepository = produitsRepository;
    }

    @Override
    public List<Produit> getAllProduit() {
        return produitsRepository.findAll();
    }

    @Override
    public Produit getProduitById(int id) {
        return produitsRepository.findById(id).get();
    }

    @Override
    public Produit getProduitByLibelle(String libelle) {
        return produitsRepository.findByLibelle(libelle);
    }

    @Override
    public Produit addProduit(Produit produit) {
        return produitsRepository.save(produit);
    }

    @Override
    public Produit updateProduit(Produit produit) {
        return produitsRepository.save(produit);
    }

    @Override
    public void deleteProduit(int id) {
        produitsRepository.deleteById(id);
    }

    @Override
    public Page<Produit> getProduitByUtilisateur(Utilisateur utilisateur, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return produitsRepository.findByUtilisateur(utilisateur, pageable);
    }

    @Override
    public List<Produit> getProduitByCriteria(Utilisateur utilisateur, List<String> tailles, List<String> couleurs, List<String> typeProduits) {
        List<Produit> produits = produitsRepository.findByUtilisateur(utilisateur);

        List<Produit> produitList = new ArrayList<>();
        List<Produit> produitList1 = new ArrayList<>();
        List<Produit> produitList2 = new ArrayList<>();

        if(produits == null){
            System.out.println("hola");
            return null;
        }

        if(typeProduits != null){
            for(Produit p: produits){
                for (String type: typeProduits){
                    if(p.getTypeProduit().getNom().contains(type)){
                        System.out.println("critere de type");
                        if(!produitList.contains(p)){
                            produitList.add(p);
                        }
                    }
                }
            }
        }

        if(produitList == null){
            produitList = produits;
        }

        if(tailles != null){
            for (Produit p: produitList){
                for(Taille t: p.getTailles()){
                    for(String taille : tailles){
                        if(t.getLibelle().contains(taille)){
                            System.out.println("critere de taille");
                            if(!produitList1.contains(p)){
                                produitList1.add(p);
                            }
                        }
                    }
                }
            }
        }

        if(produitList1 == null){
            produitList1 = produitList;
        }

        if(couleurs != null){
            for (Produit p: produitList1){
                for(Couleurs c: p.getCouleurs()){
                    for(String couleur : couleurs){
                        if(c.getNom().contains(couleur)){
                            System.out.println("critere de couleur");
                            if(!produitList2.contains(p)){
                                produitList2.add(p);
                            }
                        }
                    }
                }
            }
        }

        if(produitList2 == null){
            produitList2 = produitList1;
        }

        return produitList2;
    }

    @Override
    public Page<Produit> getProduitByCategorie(Categorie categorie, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return produitsRepository.findByCategorie(categorie, pageable);
    }

    @Override
    public Page<Produit> getProduitByTypeProduit(TypeProduit typeProduit, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return produitsRepository.findByTypeProduit(typeProduit, pageable);
    }

    //pour récupérer les produits les plus vendus
    @Override
    public Page<Produit> getProduitBySold(int pageNo) {
        //un dictionnaire qui portera chaque produit et son nombre de ventes
        Map<String,Integer> dictionnaireDesVentes = new HashMap<>();

        Pageable pageable = PageRequest.of(pageNo, 25);

        List<Produit> produits = new ArrayList<>();

        List<Commande> commandesLivrer = commandeService.getCommandeByEtats("livrer");
        if(commandesLivrer == null){
            return null;
        }

        for (Commande c: commandesLivrer) {
            //pour chaque commande livrer je récupère ses lignes commandes et je compte les produits pour les notés comme plus vendu
            List<LigneCommandes> ligneCommandes = new ArrayList<>();
            ligneCommandes = c.getLigneCommandes();
            for(LigneCommandes lc: ligneCommandes){
                Produit produit = lc.getProduit();
                if(produit == null){
                    return null;
                }
                dictionnaireDesVentes.putIfAbsent(produit.getLibelle(),1);

                if(dictionnaireDesVentes.containsKey(produit.getLibelle())){
                    //je récupère le nombre de fois pour mettre à jour
                   int nombre = dictionnaireDesVentes.get(produit.getLibelle());
                   nombre += 1;
                   dictionnaireDesVentes.replace(produit.getLibelle(), nombre);
                }
            }
        }

        if(dictionnaireDesVentes == null){
            return null;
        }

        //convertir le dictionnaire en une liste d'entrer
        List<Map.Entry<String, Integer>> listeMaps = new ArrayList<>(dictionnaireDesVentes.entrySet());

        //variable pour trier les dictionnaires
        Comparator<Map.Entry<String, Integer>> comparateurDentrer = new Comparator<Map.Entry<String, Integer>>(){

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        };

        //on tri notre map
        Collections.sort(listeMaps, comparateurDentrer);

        //on doit parcourir le dictionnaire pour récupérer ces produits et les ajouter a la liste par priorité
        for(Map.Entry<String, Integer> dictionnaire : listeMaps){
            Produit produit = produitsRepository.findByLibelle(dictionnaire.getKey());
            produits.add(produit);
        }

        if(produits == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, produits.size());

        // Récupérez la liste de produits pour la page
        List<Produit> contenuPage = produits.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Produit> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), produits.size());

        // Retournez la page de produits
        return page;
    }

    //pour extraire les produits les mieux notés
    @Override
    public Page<Produit> getProduitByFavorite(int pageNo) {
        //un dictionnaire qui portera chaque produit et son nombre de favories
        Map<String,Integer> dictionnaireDesFavories = new HashMap<>();

        Pageable pageable = PageRequest.of(pageNo, 25);

        List<Produit> produits = new ArrayList<>();

        List<Favories> favories = favorieService.getAllFavories();
        if(favories == null){
            return null;
        }

        for (Favories f: favories) {
            //pour chaque favorie, je compte les produits pour les marqués comme plus notés
            List<Produit> produitFavories = f.getProduit();
            for(Produit p : produitFavories){
                dictionnaireDesFavories.putIfAbsent(p.getLibelle(),1);

                if(dictionnaireDesFavories.containsKey(p.getLibelle())){
                    //je récupère le nombre de fois pour mettre à jour
                    int nombre = dictionnaireDesFavories.get(p.getLibelle());
                    nombre += 1;
                    dictionnaireDesFavories.replace(p.getLibelle(), nombre);
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
            Produit produit = produitsRepository.findByLibelle(dictionnaire.getKey());
            produits.add(produit);
        }

        if(produits == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, produits.size());

        // Récupérez la liste de produits pour la page
        List<Produit> contenuPage = produits.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Produit> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), produits.size());

        // Retournez la page de produits
        return page;
    }

    @Override
    public List<Produit> getByTypeProduit(TypeProduit typeProduit) {
        return produitsRepository.findByTypeProduit(typeProduit);
    }

    @Override
    public Page<Produit> getProduitByCouleurs(Couleurs couleurs, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return produitsRepository.findByCouleurs(couleurs, pageable);
    }

    @Override
    public Page<Produit> getProduitByTaille(Taille taille, int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        return produitsRepository.findByTailles(taille, pageable);
    }

    @Override
    public Page<Produit> pageProduit(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 12);
        Page<Produit> pageProduit = produitsRepository.pageProduit(pageable);
        return pageProduit;
    }

    //retourner les favorie d'un client
    @Override
    public Page<Produit> getProduitByFavoriteClient(int pageNo, Client client) {

        Client client1 = clientService.getClientById(client.getIdUser());
        if(client1 == null){
            return null;
        }
        List<Favories> favories = client1.getFavories();

        if(favories == null){
            return null;
        }

        List<Produit> produits = new ArrayList<>();

        for(Favories f: favories){
            List<Produit> produitList = f.getProduit();
            for(Produit p: produitList){
                produits.add(p);
            }
        }

        if(produits == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, produits.size());

        // Récupérez la liste de produits pour la page
        List<Produit> contenuPage = produits.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Produit> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), produits.size());

        // Retournez la page de produits
        return page;
    }

    @Override
    public Page<Produit> getProduitBoutiqueByTagName(int idBoutique, String name, int pageNo) {
        Boutique boutique = boutiqueService.getBoutiqueById(idBoutique);
        if(boutique == null){
            return null;
        }

        List<Produit> allProduits = produitsRepository.findByUtilisateur(boutique.getUtilisateur());
        List<Produit> produits = new ArrayList<>();
        if(allProduits == null){
            return null;
        }
        for(Produit p: allProduits){
            if(p.getLibelle().contains(name.trim()) || p.getLibelle().toUpperCase().contains(name.trim())){
                produits.add(p);
            }
        }

        if(produits == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, produits.size());

        // Récupérez la liste de produits pour la page
        List<Produit> contenuPage = produits.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Produit> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), produits.size());

        // Retournez la page de produits
        return page;
    }


    @Override
    public List<Produit> getByUtilisateur(Utilisateur utilisateur) {
        return produitsRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public Page<Produit> getProduitByTagName(String name, int pageNo) {
        List<Produit> allProduits = produitsRepository.findAll();
        List<Produit> produits = new ArrayList<>();
        if(allProduits == null){
            return null;
        }
        for(Produit p: allProduits){
            if(p.getLibelle().contains(name.trim()) || p.getLibelle().toUpperCase().contains(name.trim())){
              produits.add(p);
            }
        }

        if(produits == null){
            return null;
        }

        //creer la page de produits à partir de la liste de produit
        int startIndex = pageNo * 25;
        int endIndex = Math.min(startIndex + 25, produits.size());

        // Récupérez la liste de produits pour la page
        List<Produit> contenuPage = produits.subList(startIndex, endIndex);

        // Créez une instance de Page à partir de la liste de produits et des paramètres de pagination
        Page<Produit> page = new PageImpl<>(contenuPage, PageRequest.of(pageNo, 25), produits.size());

        // Retournez la page de produits
        return page;
    }
}


