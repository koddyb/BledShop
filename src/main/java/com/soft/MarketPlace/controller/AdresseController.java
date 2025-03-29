package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Utilisateur;
import com.soft.MarketPlace.service.AdresseService;
import com.soft.MarketPlace.service.ClientService;
import com.soft.MarketPlace.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/adresse")
public class AdresseController {

    @Autowired
    AdresseService adresseService;

    @Autowired
    ClientService clientService;

    @Autowired
    UtilisateurService utilisateurService;

    //ajouter une adresse au client
    @PostMapping("/client/ajouter")
    public String assignAdresse(Model model, Principal principal, @RequestParam("adresse") List<Integer> idAdresses){
        if(principal == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        Adresse adresse1 = adresseService.getAdresseById(idAdresses.get(0));

        Adresse adresse2 = adresseService.getAdresseById(idAdresses.get(1));

        List<Adresse> adressesToAdd = new ArrayList<>();

        if(adresse1 == null || adresse2 == null){
            model.addAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/commande/paiement";
        }

        adressesToAdd.add(adresse1);
        adressesToAdd.add(adresse2);

        String userName = principal.getName();
        Client client = clientService.getClientByEmail(userName);
        Client clientToUpdate = clientService.getClientById(client.getIdUser());
        List<Adresse> adresses = adresseService.getAdresseByUser(client);

        if(adresses.isEmpty() || adresses == null){
           clientToUpdate.setAdresseUser(adressesToAdd);
           clientService.addClient(clientToUpdate);
        }
        model.addAttribute("success", "vos adresses ont bien étés ajoutées");
        return "redirect:/commande/paiement";
    }

    //modifier une adresse du client
    @PostMapping("/client/modifier")
    public String formModifierAdresse(RedirectAttributes model, @RequestParam(name = "adresse1", required = false,defaultValue = "0") int idAdresse1,@RequestParam(name = "adresse2", required = false,defaultValue = "0") int idAdresse2, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        Client client = clientService.getClientByEmail(userName);
        Client clientToUpdate = clientService.getClientById(client.getIdUser());
        List<Adresse> adresses = clientToUpdate.getAdresseUser();
        if(idAdresse1 == 0 && idAdresse2 == 0){
            return "redirect:/compte";
        }
        if(idAdresse1 != 0){
            Adresse adresse = adresseService.getAdresseById(idAdresse1);
            if(adresse != null)
                adresses.remove(0);
                adresses.add(0,adresse);
        }
        if(idAdresse2 != 0){
            Adresse adresse = adresseService.getAdresseById(idAdresse2);
            if(adresse != null)
                adresses.remove(1);
                adresses.add(1,adresse);
        }

        if(adresses.isEmpty() || adresses == null){
            model.addFlashAttribute("error", "une erreur est survenue veillez réessayer");
            return "redirect:/compte";
        }

        clientToUpdate.setAdresseUser(adresses);
        clientService.addClient(clientToUpdate);
        model.addFlashAttribute("success", "information modifier avec succes");
        return "redirect:/compte";
    }

    //ajouter une adresse a un utilisateur
    @PostMapping("/utilisateur/ajouter")
    public String assignUserAdresse(Model model, Principal principal, @RequestParam("adresse") List<Integer> idAdresses, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        Adresse adresse1 = adresseService.getAdresseById(idAdresses.get(0));

        Adresse adresse2 = adresseService.getAdresseById(idAdresses.get(1));

        List<Adresse> adressesToAdd = new ArrayList<>();

        if(adresse1 == null || adresse2 == null){
            ra.addFlashAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/commande/paiement";
        }

        adressesToAdd.add(adresse1);
        adressesToAdd.add(adresse2);

        String userName = principal.getName();
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        Utilisateur utilisateurToUpdate = utilisateurService.getUtilisateurById(utilisateur.getIdUser());
        List<Adresse> adresses = adresseService.getAdresseByUser(utilisateur);

        if(adresses.isEmpty() || adresses == null){
            utilisateurToUpdate.setAdresseUser(adressesToAdd);
            utilisateurService.addUtilisateur(utilisateurToUpdate);
        }
        ra.addFlashAttribute("success", "vos adresses ont bien étés ajoutées");
        return "redirect:/utilisateur/compte";
    }

    //modifier une adresse d'utilisateur
    @PostMapping("/utilisateur/modifier")
    public String formModifierAdresseUtilisateur(RedirectAttributes model, @RequestParam(name = "adresse1", required = false,defaultValue = "0") int idAdresse1,@RequestParam(name = "adresse2", required = false,defaultValue = "0") int idAdresse2, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        Utilisateur utilisateurToUpdate = utilisateurService.getUtilisateurById(utilisateur.getIdUser());
        List<Adresse> adresses = utilisateurToUpdate.getAdresseUser();
        if(idAdresse1 == 0 && idAdresse2 == 0){
            return "redirect:/compte";
        }
        if(idAdresse1 != 0){
            Adresse adresse = adresseService.getAdresseById(idAdresse1);
            if(adresse != null)
                adresses.remove(0);
            adresses.add(0,adresse);
        }
        if(idAdresse2 != 0){
            Adresse adresse = adresseService.getAdresseById(idAdresse2);
            if(adresse != null)
                adresses.remove(1);
            adresses.add(1,adresse);
        }

        if(adresses.isEmpty() || adresses == null){
            model.addFlashAttribute("error", "une erreur est survenue veillez réessayer");
            return "redirect:/compte";
        }

        utilisateurToUpdate.setAdresseUser(adresses);
        utilisateurService.addUtilisateur(utilisateurToUpdate);
        model.addFlashAttribute("success", "information modifier avec succes");
        return "redirect:/compte";
    }
}
