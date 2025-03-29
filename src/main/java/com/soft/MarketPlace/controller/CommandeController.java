package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.service.*;
import com.soft.MarketPlace.serviceImplement.ApiService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.annotations.LazyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    CommandeService commandeService;

    @Autowired
    ClientService clientService;

    @Autowired
    AdresseService adresseService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    ApiService apiService = new ApiService();

    //tout ce qui concerne les commandes

    //coté client
    @GetMapping("/paiement")
    public String payerPanier(Model model, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        List<Adresse> adresses = adresseService.getAllAdresse();

        String userName = principal.getName();

        Client client = clientService.getClientByEmail(userName);

        String nomPrenom = client.getPrenom() + " " +client.getNom();

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
        List<String> numeroBoutiquier = new ArrayList<>();
        Panier panier = client.getPanier();

        if (panier == null){
            ra.addFlashAttribute("error", "une erreur s'est produite veillez reessayer");
            return "redirect:/compte";
        }

        if(panier.getLignePaniers().isEmpty() || panier.getLignePaniers() == null){
            return "redirect:/compte";
        }

        for(LignePaniers lp: panier.getLignePaniers()){
            if(!numeroBoutiquier.contains(lp.getProduit().getUtilisateur().getTelephone1())){
                numeroBoutiquier.add(lp.getProduit().getUtilisateur().getTelephone1());
            }
            if(!numeroBoutiquier.contains(lp.getProduit().getUtilisateur().getTelephone2())){
                numeroBoutiquier.add(lp.getProduit().getUtilisateur().getTelephone2());
            }
        }


        model.addAttribute("client", client);
        model.addAttribute("numeros", numeroBoutiquier);
        return "/client/paiement";
    }

    @PostMapping("/payer")
    public String passerCommande(Principal principal, Model model, @RequestParam("telephone") String telephone, @RequestParam("adresse") int idAdresse, RedirectAttributes ra, HttpServletRequest request) throws MessagingException {

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        Adresse adresseLivraison = adresseService.getAdresseById(idAdresse);
        if(adresseLivraison == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/compte";
        }

        String userName = principal.getName();
        Client client = clientService.getClientByEmail(userName);
        Panier panier = client.getPanier();

        if (panier == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/compte";
        }

        if(panier.getLignePaniers().isEmpty() || panier.getLignePaniers() == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/compte";
        }

        //paiement
        if(apiService.BuyCommand(client,telephone, (int) panier.getPrixTotal(),adresseLivraison.getLocalisation()) != null){
            //fin paiement
            //commandeService.addCommande(panier,adresseLivraison.getLocalisation(),telephone);
            String paymentUrl = apiService.BuyCommand(client,telephone, (int) panier.getPrixTotal(),adresseLivraison.getLocalisation());
            return "redirect:" + paymentUrl;
            /*ra.addFlashAttribute("success", "la commande a été passée avec sucess");
            return "redirect:/compte";*/
        }
        else
            ra.addFlashAttribute("error","échec de paiement");
            return "redirect:/compte";

    }

    @GetMapping("/confirmer/{token}/{adresse}")
    private String paymentResult(HttpServletRequest request,
                                 RedirectAttributes ra,
                                 @RequestParam("status") String status,
                                 @RequestParam("phone") String phone,
                                 @PathVariable("token") String token,
                                 @RequestParam("transaction_id") String transaction_id,
                                 @RequestParam("sign") String sign,
                                 @PathVariable("adresse") String adresse,
                                 Principal principal,
                                 Model model
                                 )
    {
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        token = token.replace("slash","/");

        String userName = principal.getName();

        Client client = clientService.getClientByEmail(userName);

        Panier panier = client.getPanier();

        if(status == "failed"){
            ra.addFlashAttribute("error","échec de paiement");
            return "redirect:/compte";
        }else{
            if(token.equals(bCryptPasswordEncoder.encode(""+(client.getCommandes().get(client.getCommandes().toArray().length - 1)).getIdCommande()))){
                commandeService.addCommande(client.getPanier(),phone,adresse.replace("kkk"," "));
                ra.addFlashAttribute("success", "la commande a été passée avec sucess");
                return "redirect:/compte";
            }else{
                ra.addFlashAttribute("error","échec de paiement");
                return "redirect:/compte";
            }
        }
    }

}