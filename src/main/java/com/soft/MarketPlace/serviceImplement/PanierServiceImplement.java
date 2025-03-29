package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.*;
import com.soft.MarketPlace.service.CouleurService;
import com.soft.MarketPlace.service.PanierService;
import com.soft.MarketPlace.service.TailleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PanierServiceImplement implements PanierService {

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private CouleurService couleurService;

    @Autowired
    private TailleService tailleService;

    @Autowired
    private LignepaniersRepository lignepaniersRepository;

    public PanierServiceImplement(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    @Override
    public List<Panier> getAllPanier() {
        return panierRepository.findAll();
    }

    @Override
    public Panier getPanierById(int id) {
        return panierRepository.findById(id).get();
    }

    @Override
    public Panier getPanierByDate(String date) {
        return panierRepository.findByDatePanier(date);
    }

    @Override
    public Panier addPanier(Panier panier) {
        return panierRepository.save(panier);
    }

    @Override
    public Panier updateLivraison(Panier panier) {
        return panierRepository.save(panier);
    }

    @Override
    public void deletePanier(int id) {
        panierRepository.deleteById(id);
    }

    @Override
    public Panier getPanierByClient(Client client) {
        return panierRepository.findByClient(client);
    }

    @Override
    public Panier ajouterLignePagnier(Produit produit, int quantite, Client client) {

        Panier panier = client.getPanier();

        if(panier == null){
            panier = new Panier();
            panier.setDatePanier(new Date());
            panier.setAbouti(false);
            panier.setValide(true);
            panier.setPrixTotal(0);
            panier.setClient(client);
            panierRepository.save(panier);
            client.setPanier(panier);
        }

        List<LignePaniers> lignePaniers = panier.getLignePaniers();
        LignePaniers lignePanier = findLignePanier(lignePaniers, produit.getIdProduit());
        if(lignePaniers == null){
            lignePaniers = new ArrayList<LignePaniers>();
            if(lignePanier == null){
                lignePanier = new LignePaniers();
                lignePanier.setProduit(produit);
                lignePanier.setPrix(quantite * produit.getPrix());
                lignePanier.setQuantite(quantite);
                lignePanier.setCouleur(produit.getCouleurs().get(0).getNom());
                lignePanier.setTaille(produit.getTailles().get(0).getLibelle());
                lignePanier.setPanier(panier);
                lignePaniers.add(lignePanier);
                lignepaniersRepository.save(lignePanier);
            }
        }else {
                if(lignePanier == null){
                    lignePanier = new LignePaniers();
                    lignePanier.setProduit(produit);
                    lignePanier.setPrix(quantite * produit.getPrix());
                    lignePanier.setQuantite(quantite);
                    List<Couleurs> couleurs = produit.getCouleurs();
                    List<Taille> tailles = produit.getTailles();
                     System.out.println("couleurs taille "+couleurs.size());
                     System.out.println("tailles taille "+tailles.size());
                    lignePanier.setCouleur(produit.getCouleurs().get(0).getNom());
                    lignePanier.setTaille(produit.getTailles().get(0).getLibelle());
                    lignePanier.setPanier(panier);
                    lignePaniers.add(lignePanier);
                    lignepaniersRepository.save(lignePanier);
                }
                else{
                lignePanier.setQuantite(lignePanier.getQuantite() + quantite);
                lignePanier.setCouleur(lignePanier.getCouleur());
                lignePanier.setTaille(lignePanier.getTaille());
                lignePanier.setPrix(lignePanier.getPrix() + (quantite * produit.getPrix()));
                lignepaniersRepository.save(lignePanier);
                }
        }
        panier.setLignePaniers(lignePaniers);

        int totalElement = totalElements(panier.getLignePaniers());
        float totalPrix = totalPrix(panier.getLignePaniers());
        panier.setPrixTotal(totalPrix);
        panier.setClient(client);
        panier.setValide(true);
        panier.setDatePanier(new Date());
        return panierRepository.save(panier);
    }

    @Override
    public Panier modifierLignePanier(Produit produit, int quantite, String couleur, String taille, Client client) {

        Panier panier = client.getPanier();

        List<LignePaniers> lignePaniers = panier.getLignePaniers();

        LignePaniers lignePanier = findLignePanier(lignePaniers, produit.getIdProduit());

        lignePanier.setTaille(taille);
        lignePanier.setCouleur(couleur);
        lignePanier.setQuantite(quantite);
        lignePanier.setPrix(quantite * produit.getPrix());
        lignepaniersRepository.save(lignePanier);

        float prixTotal = totalPrix(lignePaniers);

        panier.setLignePaniers(lignePaniers);
        panier.setPrixTotal(prixTotal);

        return panierRepository.save(panier);
    }

    @Override
    public Panier supprimerLignePanier(Produit produit, Client client) {
        Panier panier = client.getPanier();

        List<LignePaniers> lignePaniers = panier.getLignePaniers();

        LignePaniers lignePanier = findLignePanier(lignePaniers, produit.getIdProduit());

        lignePaniers.remove(lignePanier);

        lignepaniersRepository.delete(lignePanier);

        float prixTotal = totalPrix(lignePaniers);

        panier.setLignePaniers(lignePaniers);
        panier.setPrixTotal(prixTotal);

        return panierRepository.save(panier);
    }

    private LignePaniers findLignePanier(List<LignePaniers> lignePaniers, int idProduit){
        if(lignePaniers == null){
            return null;
        }

        LignePaniers lignePanier = null;

        for (LignePaniers lp:lignePaniers) {
            if(lp.getProduit().getIdProduit() == idProduit){
                lignePanier = lp;
            }
        }
        return lignePanier;
    }

    private int totalElements(List<LignePaniers> lignePaniers){
        int totalElement = 0;

        for(LignePaniers lp: lignePaniers){
            totalElement += lp.getQuantite();
        }
        return totalElement;
    }

    private float totalPrix(List<LignePaniers> lignePaniers){
        float totalPrix = 0;

        if(lignePaniers == null){
            return totalPrix;
        }

        for(LignePaniers lp: lignePaniers){
            totalPrix += lp.getPrix();
        }
        return totalPrix;
    }
}
