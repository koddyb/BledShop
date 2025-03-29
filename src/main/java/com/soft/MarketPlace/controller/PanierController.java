package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.ReaderEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class PanierController {

    @Autowired
    public ClientService clientService;

    @Autowired
    public PanierService panierService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private TypeProduitService typeProduitService;

    @Autowired
    public ProduitService produitService;

    @GetMapping("/panier")
    public String getPanier(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "/login";
        }

        //traitement du menu

        if(principal != null){
            String userName = principal.getName();
            Client client = clientService.getClientByEmail(userName);
            Panier panier = panierService.getPanierByClient(client);
            List<LignePaniers> lignePaniers = panier.getLignePaniers();
            int taille = lignePaniers.size();
            String name = client.getNom() +" "+ client.getPrenom();
            model.addAttribute("panier", panier);
            model.addAttribute("nombreDeProduit", taille);
            model.addAttribute("connected", name);
        }

        List<TypeProduit> typeProduitVetement = typeProduitService.getTypeProduitByCategorie(categorieService.getCategorieByLibelle("vetements"));
        List<TypeProduit> typeProduitAccessoire = typeProduitService.getTypeProduitByCategorie(categorieService.getCategorieByLibelle("accessoires"));
        List<TypeProduit> typeProduitChaussure = typeProduitService.getTypeProduitByCategorie(categorieService.getCategorieByLibelle("chaussures"));

        model.addAttribute("vetements", typeProduitVetement);
        model.addAttribute("chaussures", typeProduitChaussure);
        model.addAttribute("accessoires", typeProduitAccessoire);
        //fin du traitement du menu

        String username = principal.getName();
        Client client = clientService.getClientByEmail(username);
        Panier panier = client.getPanier();
        if(panier == null){
            model.addAttribute("vide", "Votre Panier Est Vide");
            return "panier";
        }
        model.addAttribute("panier", panier);
        List<LignePaniers> lignePaniersList = panier.getLignePaniers();
        int nombreProduit = lignePaniersList.size();
        if(nombreProduit == 0){
            model.addAttribute("vide", "Votre Panier Est Vide");
            return "panier";
        }
        model.addAttribute("nombre", nombreProduit);
      return "panier";
    }

    @PostMapping("/panier/add")
    public String addProduitToCart(
            @RequestParam(value = "quantite", required = false, defaultValue = "1")int quantite,
            @RequestParam("id")int idProduit,
            HttpServletRequest request,
            Model model,
            Principal principal
            ){
// essayons d'ajouter le produit au panier
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "/login";
        }
        Produit produit = produitService.getProduitById(idProduit);
        String userName = principal.getName();
        Client client = clientService.getClientByEmail(userName);
        Panier panier = panierService.ajouterLignePagnier(produit, quantite, client);
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/panier/modifier", method = RequestMethod.POST)
    public String UpdateProduitToCart(
            @RequestParam(value = "quantite", required = false, defaultValue = "1")int quantite,
            @RequestParam("id")int idProduit,
            @RequestParam("couleur")String couleur,
            @RequestParam("taille")String taille,
            Model model,
            Principal principal){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }else{
            String userName = principal.getName();
            Client client = clientService.getClientByEmail(userName);
            Produit produit = produitService.getProduitById(idProduit);
            Panier panier = panierService.modifierLignePanier(produit, quantite, couleur, taille, client);
            model.addAttribute("panier", panier);
            List<LignePaniers> lignePaniersList = panier.getLignePaniers();
            int nombreProduit = lignePaniersList.size();
            model.addAttribute("nombre", nombreProduit);
            return "redirect:/panier";
        }
    }

    @RequestMapping(value = "/panier/supprimer/{id}", method = RequestMethod.GET)
    public String deleteProduitToCart(@PathVariable("id") int idProduit, Model model, Principal principal, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }else{
            String userName = principal.getName();
            Client client = clientService.getClientByEmail(userName);
            Produit produit = produitService.getProduitById(idProduit);
            Panier panier = panierService.supprimerLignePanier(produit, client);
            model.addAttribute("panier", panier);
            List<LignePaniers> lignePaniersList = panier.getLignePaniers();
            int nombreProduit = lignePaniersList.size();
            model.addAttribute("nombre", nombreProduit);
            ra.addFlashAttribute("success", "l'article a été enlevé de votre panier");
            return "redirect:/panier";
        }
    }
}
