package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Favories;
import com.soft.MarketPlace.entity.Produit;
import com.soft.MarketPlace.service.BoutiqueService;
import com.soft.MarketPlace.service.ClientService;
import com.soft.MarketPlace.service.FavorieService;
import com.soft.MarketPlace.service.ProduitService;
import com.soft.MarketPlace.serviceImplement.ClientsServiceImplement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FavorieController {

    @Autowired
    ClientService clientsService;

    @Autowired
    FavorieService favorieService;

    @Autowired
    ProduitService produitService;

    @Autowired
    BoutiqueService boutiqueService;


    @GetMapping("/favorie/ajouter/{id}")
    public String AddProduitToFavorie(@PathVariable("id") int idProduit, Principal principal, HttpServletRequest request, Model model, RedirectAttributes ra){

        if (principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        Client client = clientsService.getClientByEmail(userName);
        Produit produit = produitService.getProduitById(idProduit);

        if(produit == null){
            ra.addFlashAttribute("error", "une erreur s'est produite veillez réessayer");
            return "redirect:" + request.getHeader("Referer");
        }

        List<Favories> favoriesList = favorieService.getFavoriesByClient(client);

        for(Favories f: favoriesList){
            if(f.getProduit().contains(produit) && f.getClient() == client){
                favorieService.deleteFavories(f.getIdFavories());
                return "redirect:" + request.getHeader("Referer");
            }
        }

        Favories favories = new Favories();
        favories.setClient(client);
        List<Produit> produitList = new ArrayList<>();
        produitList.add(produit);
        favories.setProduit(produitList);
        favories.setNombres(0);
        favorieService.addFavories(favories);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/favorie/boutique/ajouter/{id}")
    public String AddBoutiqueToFavorite(@PathVariable("id") int idBoutique, Principal principal, HttpServletRequest request, Model model, RedirectAttributes ra){

        if (principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        Client client = clientsService.getClientByEmail(userName);
        Boutique boutique = boutiqueService.getBoutiqueById(idBoutique);

        if(boutique == null){
            ra.addFlashAttribute("error", "une erreur s'est produite veillez réessayer");
            return "redirect:" + request.getHeader("Referer");
        }

        List<Favories> favoriesList = favorieService.getFavoriesByClient(client);

        for(Favories f: favoriesList){
            if(f.getBoutiques().contains(boutique) && f.getClient() == client){
                favorieService.deleteFavories(f.getIdFavories());
                return "redirect:" + request.getHeader("Referer");
            }
        }

        Favories favories = new Favories();
        favories.setClient(client);
        List<Boutique> boutiqueList = new ArrayList<>();
        boutiqueList.add(boutique);
        favories.setBoutiques(boutiqueList);
        favories.setNombres(0);
        favorieService.addFavories(favories);
        return "redirect:" + request.getHeader("Referer");
    }

}
