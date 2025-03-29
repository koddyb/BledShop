package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Utilisateur;
import com.soft.MarketPlace.service.AdresseService;
import com.soft.MarketPlace.service.ClientService;
import com.soft.MarketPlace.service.CommandeService;
import com.soft.MarketPlace.service.UtilisateurService;
import com.soft.MarketPlace.serviceImplement.ApiService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Controller
public class Compte {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    CommandeService commandeService;

    @Autowired
    ClientService clientService;

    @Autowired
    AdresseService adresseService;

    ApiService api = new ApiService();

    public String messageToSend2(int code) {
        return "Bonjour monsieur/madame nous vous envoyons ce mail"
                + " parceque vous venez de faire une demande de modification de mot de passe. </br>"
                + " Afin de poursuivre votre opération, vous devez soumettre le code <h3> "
                + code +"</h3> dans le site pour procéder à la vérification de votre adresse.</br>"
                + " Merci pour votre compréhension n'hésitez pas a soumettre vos questions par whatsapp"
                + " au +237698765588 à bientot.";
    }
    //variable pour le nombre aleatoire
    int nombreToSend = 0;

    @GetMapping("/compte")
    public String accueilClient(Model model, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        List<Adresse> adresses = adresseService.getAllAdresse();

        String userName = principal.getName();

        Client client = clientService.getClientByEmail(userName);

        String nomPrenom = client.getPrenom() + " " +client.getNom();

        List<Commande> commandeAttente = commandeService.getCommandeByEtat(client, "en attente");

        List<Commande> commandeHistorique = client.getCommandes();

        if(!client.getIsValide()){

            if(client.getAdresseUser() == null || client.getAdresseUser().isEmpty() ){

                model.addAttribute("adresseVide", "adresses vides");
                model.addAttribute("adresses", adresses);
                model.addAttribute("client", client);
                model.addAttribute("clientInformations", nomPrenom);
                model.addAttribute("verification", "s'il vous plait veillez ajouter des adresses pour la livraison");
                return "/client/espace-client";
            }

            if(!client.getIsTelephone1Valide()){
                model.addAttribute("telephone1Valide", "non valide");
            }

            if(!client.getIsTelephone2Valide()){
                model.addAttribute("telephone2Valide", "non valide");
            }

            model.addAttribute("client", client);
            model.addAttribute("numeroNonConforme", "numéros non conforme");
            model.addAttribute("clientInformations", nomPrenom);
            model.addAttribute("verification", "enfin veillez confirmer vos numéro de paiement");
            return "/client/espace-client";
        }

        model.addAttribute("client", client);
        model.addAttribute("adresses", adresses);
        model.addAttribute("clientInformations", nomPrenom);
        model.addAttribute("commandeAttente", commandeAttente);
        model.addAttribute("commandeHistorique", commandeHistorique);
        return "/client/espace-client";
    }

    //mot de passe oublié

    //methode qui retourne le formulaire du mail pour la recupération du mot de passe
    @GetMapping("/compte/password/recuver")
    public String recuverPassword(Model model) {
        return "formEmail";
    }

    //méthode qui va envoyer le mail a l'utilisateur souhaitant récupérer son password
    @PostMapping("/compte/verifier")
    public String verifyEmail(@RequestParam("username") String userName, Model model, RedirectAttributes ra) throws AddressException, MessagingException {
        System.out.println("mail: "+userName);

        Client client = clientService.getClientByEmail(userName);

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        if(client == null && utilisateur == null){
            ra.addFlashAttribute("error", "aucun compte ne correspond à cette adresse!");
            return "redirect:/compte/password/recuver";
        }

        if(client != null){
            System.out.println("mail existant: "+userName);
            nombreToSend = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
            System.out.println("nombre généré: "+nombreToSend);
            int nombre = nombreToSend+1024;
            api.sendmail(userName, messageToSend2(nombreToSend).indent(1), "Récupération Du Mot De Passe");
            System.out.println("code envoyé: "+nombreToSend);
            model.addAttribute("client", client);
            model.addAttribute("nombre", nombre);
            return "client/recuperation-password";
        }

        if(utilisateur != null){
            System.out.println("mail existant: "+userName);
            nombreToSend = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
            System.out.println("nombre généré: "+nombreToSend);
            int nombre = nombreToSend+1024;
            api.sendmail(userName, messageToSend2(nombreToSend).indent(1), "Récupération Du Mot De Passe");
            System.out.println("code envoyé: "+nombreToSend);
            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("nombre", nombre);
            return "utilisateur/recuperation-password";
        }

        ra.addFlashAttribute("error", "une erreur s'est produite veillez réessayer");
        return "redirect:compte/password/recuver";
    }
}
