package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.Boutique;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Utilisateur;
import com.soft.MarketPlace.service.BoutiqueService;
import com.soft.MarketPlace.service.CommandeService;
import com.soft.MarketPlace.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Controller
public class UserController {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    BoutiqueService boutiqueService;

    @Autowired
    CommandeService commandeService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String redirectToHome(Principal principal, Model model, RedirectAttributes ra){
        //on verifie le type d'utilisateur authentifier
        Authentication auth = (Authentication) principal;
        if(auth == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";

        }
        if(auth.isAuthenticated()){
           String role = auth.getAuthorities().toString();
         if(role.contains("CLIENT")){
             System.out.println("client connecté");
             return "redirect:/produit/liste/0";
         }else if(role.contains("UTILISATEUR")){
             //on recupere l'utilisateur
             Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
             //on vérifie s'il a déja une boutique ou pas
             List<Boutique> boutique = boutiqueService.getBoutiqueByUtilisateur(utilisateur);
             String nomPrenom = utilisateur.getPrenom() + " " +utilisateur.getNom();
             if(boutique.isEmpty()){
                 return "choose-type-boutique";
             }else{
                 //on verifie que l'utilisateur est vérifié
                 if(!utilisateur.getIsTelephone1Valide()){
                     model.addAttribute("telephone1", "votre premier numéro de transaction n'a pas encore été confirmé veillez vous rendre dans la session compte");
                 }
                 if(!utilisateur.getIsTelephone1Valide()){
                     model.addAttribute("telephone2", "votre second numéro de transaction n'a pas encore été confirmé veillez vous rendre dans la session compte");
                 }
                 if(!utilisateur.getIsValide()){
                     model.addAttribute("invalide", "votre compte est en attente de validation par nos agents veillez patienter s'il vous plait");
                     model.addAttribute("utilisateur", utilisateur);
                     return "utilisateur/espace-utilisateur";
                 }

                 List<Commande> commandes = new ArrayList<Commande>();
                 commandes = commandeService.getCommandeByUtilisateur(utilisateur);
                 model.addAttribute("commandes", commandes);
                 model.addAttribute("utilisateur", utilisateur);
                 return "utilisateur/espace-utilisateur";
             }
         }else{
             auth.setAuthenticated(false);
             model.addAttribute("connexion", "vous devez vous connecter");
             return "login";
         }
        }
        return "/login";
    }

    @GetMapping("/client")
    public String toHome(){
        return "redirect:/home";
    }

    @GetMapping("/utilisateur")
    public String goHome(){
        return "redirect:/home";
    }

    @GetMapping("/")
    public String Home(){
        return "redirect:/produit/liste/0";
    }
}
