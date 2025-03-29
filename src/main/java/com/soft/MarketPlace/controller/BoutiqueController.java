package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/boutique")
public class BoutiqueController {
    @Autowired
    private BoutiqueService boutiqueService;

    @Autowired
    TypeBoutiqueService typeBoutiqueService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    ProduitService produitService;

    @Autowired
    TypeProduitService typeProduitService;

    @Autowired
    CategorieService categorieService;

    @Autowired
    ClientService clientService;

    @Autowired
    PanierService panierService;

    //toutes les methode sur les boutiques
    @GetMapping("/liste/{pageNo}")
    public String touteLesBoutiques(Model model, @RequestParam(defaultValue = "0") int pageNo, RedirectAttributes boutiqueMessage, Principal principal){
        //try {
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

            Page<Boutique> boutiques = boutiqueService.getAllBoutique(pageNo);
            model.addAttribute("boutiques", boutiques);
            model.addAttribute("pageCourante", pageNo);
            model.addAttribute("totalBoutiques", boutiques.getTotalElements());
            model.addAttribute("total", boutiques.getTotalPages());
            model.addAttribute("taille", boutiques.getSize());
        //}catch (Exception e){
         //   model.addAttribute("erreur", "une erreur s'est produite");
        //}
        return "liste-all-boutique";
    }

    @GetMapping("/commencer")
    public String creerBoutiqueType(Principal principal,@RequestParam("type") String type, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        Boutique boutique = new Boutique();
        if(type != null){
            if(type.contains("hommes")){
                TypeBoutique typeBoutique = typeBoutiqueService.getTypeBoutiqueByLibelle("Hommes");
                boutique.setTypeBoutique(typeBoutique);
                model.addAttribute("type", typeBoutique);
                model.addAttribute("boutique", boutique);
                return "form-ajout-boutique";
            } else if (type.contains("femmes")) {
                TypeBoutique typeBoutique = typeBoutiqueService.getTypeBoutiqueByLibelle("Femmes");
                boutique.setTypeBoutique(typeBoutique);
                model.addAttribute("type", typeBoutique);
                model.addAttribute("boutique", boutique);
                return "form-ajout-boutique";
            } else if (type.contains("enfants")) {
                TypeBoutique typeBoutique = typeBoutiqueService.getTypeBoutiqueByLibelle("Enfants");
                boutique.setTypeBoutique(typeBoutique);
                model.addAttribute("type", typeBoutique);
                model.addAttribute("boutique", boutique);
                return "form-ajout-boutique";
            }else if (type.contains("mixte")){
                TypeBoutique typeBoutique = typeBoutiqueService.getTypeBoutiqueByLibelle("Mixte");
                boutique.setTypeBoutique(typeBoutique);
                model.addAttribute("type", typeBoutique);
                model.addAttribute("boutique", boutique);
                return "form-ajout-boutique";
            }else {
                ra.addFlashAttribute("error", "désolé un erreur s'est produite");
                return "redirect:/home";
            }
        }else{
            ra.addFlashAttribute("error", "désolé un erreur s'est produite");
            return "redirect:/home";
        }
    }

    @RequestMapping(value = "/creer", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String creeBoutiquePost(@RequestParam("image") MultipartFile multipartFile, @ModelAttribute("boutique") Boutique boutique,Principal principal, RedirectAttributes ra, Model model) throws IOException {

        if(multipartFile.getOriginalFilename().endsWith(".jpg") || multipartFile.getOriginalFilename().endsWith(".jpeg") || multipartFile.getOriginalFilename().endsWith(".png")) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if( multipartFile.getSize()<=5000000 && multipartFile.getSize()>0) {
                    File image = new File("src/main/resources/static/images/boutiques/"+fileName);
                    image.createNewFile();
                    FileOutputStream fout = new FileOutputStream(image);
                    fout.write( multipartFile.getBytes());
                    fout.close();
                    Boutique boutiqueToAdd = new Boutique();
                    boutiqueToAdd.setTypeBoutique(boutique.getTypeBoutique());
                    boutiqueToAdd.setNom(boutique.getNom());
                    boutiqueToAdd.setEmplacement(boutique.getEmplacement());
                    boutiqueToAdd.setSlogan(boutique.getSlogan());
                    boutiqueToAdd.setImmatriculationRccm(boutique.getImmatriculationRccm());
                    boutiqueToAdd.setLogo(fileName);
                    //gestion de l'auteur
                    Authentication auth = (Authentication) principal;
                if(principal == null){
                    model.addAttribute("connexion", "vous devez vous connecter");
                    return "login";
                }
                    if(auth != null && auth.isAuthenticated()){
                        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
                        boutiqueToAdd.setUtilisateur(utilisateur);
                        boutiqueService.addBoutique(boutiqueToAdd);
                        ra.addFlashAttribute("success", "la boutique a été crée avec success !");
                        return "redirect:/home";
                    }else {
                        ra.addFlashAttribute("error", "une erreur s'est produite veillez réesayer !");
                        return "redirect:/home";
                    }
            }
            else {
                model.addAttribute("error", "veillez verifier la taille de votre fichier !");
                model.addAttribute("type", boutique.getTypeBoutique());
                model.addAttribute("boutique", boutique);
                return "form-ajout-boutique";
            }
        }
        else {
            model.addAttribute("error", "votre type d'image n'est pas pris en compte !");
            model.addAttribute("type", boutique.getTypeBoutique());
            model.addAttribute("boutique", boutique);
            return "form-ajout-boutique";
        }
    }

//liste des produits d'une boutique
    @GetMapping("/liste/produit/{pageNo}/{boutique}")
    public String getProduitByBoutique(Principal principal, @PathVariable("boutique") int idBoutique,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra){
        Boutique boutique = boutiqueService.getBoutiqueById(idBoutique);
        Utilisateur utilisateur = utilisateurService.getUtilisateurByBoutique(boutique);
        Page<Produit> produits = produitService.getProduitByUtilisateur(utilisateur,pageNo);

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

        List<String> couleurs = new ArrayList<>();
        List<String> tailles = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        if(produits == null){

        }else{
            for (Produit p: produits){
                for(Couleurs c: p.getCouleurs()){
                    if(! couleurs.contains(c.getNom())){
                        couleurs.add(c.getNom());
                    }
                }

                for (Taille t : p.getTailles()){
                    if(! tailles.contains(t.getLibelle())){
                        tailles.add(t.getLibelle());
                    }
                }

                if(! categories.contains(p.getTypeProduit().getNom())){
                    categories.add(p.getTypeProduit().getNom());
                }
            }
        }

        model.addAttribute("produits", produits);
        model.addAttribute("boutique", boutique);

        model.addAttribute("couleurs", couleurs);
        model.addAttribute("tailles", tailles);
        model.addAttribute("categories", categories);

        model.addAttribute("taille", produits.getSize());
        model.addAttribute("total", produits.getTotalElements());
        model.addAttribute("elements", produits.getTotalPages());
        model.addAttribute("pageCourante", pageNo);
        return "liste-produit-par-boutique";
    }

    //liste filtrée des produits d'une boutique
    @PostMapping("/produit/liste/filter")
    public String getProduitFilterByBoutique(Principal principal, @RequestParam("boutique") int idBoutique,@RequestParam(value = "categories", required = false) List<String> categoriesFilter,@RequestParam(value = "couleurs", required = false) List<String> couleursFilter,@RequestParam(value = "tailles", required = false) List<String> taillesFilter, Model model, RedirectAttributes ra){
        Boutique boutique = boutiqueService.getBoutiqueById(idBoutique);
        Utilisateur utilisateur = utilisateurService.getUtilisateurByBoutique(boutique);
        List<Produit> produits1 = produitService.getProduitByCriteria(utilisateur,taillesFilter,couleursFilter,categoriesFilter);
        List<Produit> produits = produitService.getByUtilisateur(utilisateur);


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

        List<String> couleurs = new ArrayList<>();
        List<String> tailles = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        if(produits == null){

        }else{
            for (Produit p: produits){
                for(Couleurs c: p.getCouleurs()){
                    if(! couleurs.contains(c.getNom())){
                        couleurs.add(c.getNom());
                    }
                }

                for (Taille t : p.getTailles()){
                    if(! tailles.contains(t.getLibelle())){
                        tailles.add(t.getLibelle());
                    }
                }

                if(! categories.contains(p.getTypeProduit().getNom())){
                    categories.add(p.getTypeProduit().getNom());
                }
            }
        }

        model.addAttribute("produits", produits1);
        model.addAttribute("boutique", boutique);
        model.addAttribute("total", produits1.size());
        model.addAttribute("couleurs", couleurs);
        model.addAttribute("tailles", tailles);
        model.addAttribute("categories", categories);
        return "liste-produit-filtrer-par-boutique";
    }

    @GetMapping("/liste/{pageNo}/{typeBoutique}")
    public String getBoutiqueByTypes(@PathVariable("typeBoutique") String typeBoutique,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra, Principal principal){
        TypeBoutique typeBoutique1 = new TypeBoutique();
        typeBoutique1 = typeBoutiqueService.getTypeBoutiqueByLibelle(typeBoutique);
        Page<Boutique> boutique = boutiqueService.getBoutiqueByTypeBoutique(typeBoutique1,pageNo);

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

        model.addAttribute("boutiques", boutique);
        model.addAttribute("typeBoutique", typeBoutique1.getLibelle());
        model.addAttribute("taille", boutique.getSize());
        model.addAttribute("total", boutique.getTotalPages());
        model.addAttribute("elements", boutique.getTotalElements());
        model.addAttribute("pageCourante", pageNo);
        return "liste-boutique-par-type";
    }

    //liste des boutiques les mieux notés
    @GetMapping("/liste/notes/{pageNo}")
    public String ListeBoutiqueNoter(Model model, @PathVariable("pageNo") int pageNo, Principal principal){
        //traitement du menu
        int taille = 0;
        Authentication auth = (Authentication) principal;

        if(principal != null){
            String userName = principal.getName();
            Client client = clientService.getClientByEmail(userName);
            if (client == null){
                return "redirect:/logout";
            }
            Panier panier = panierService.getPanierByClient(client);
            if(panier != null){
                List<LignePaniers> lignePaniers = panier.getLignePaniers();
                taille = lignePaniers.size();
                model.addAttribute("panier", panier);
            }
            String name = client.getNom() +" "+ client.getPrenom();
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

        try{
            Page<Boutique> boutiques = boutiqueService.getBoutiqueByFavorite(pageNo);

            if(boutiques == null){
                return "liste-all-boutique-favorite";
            }

            if(pageNo > boutiques.getTotalPages()){
                boutiques = boutiqueService.getBoutiqueByFavorite(pageNo);
            }

            model.addAttribute("boutiques", boutiques);
            model.addAttribute("taille", boutiques.getSize());
            model.addAttribute("total", boutiques.getTotalPages());
            model.addAttribute("totalBoutiques", boutiques.getTotalElements());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-boutique-favorite";

        }catch (IllegalArgumentException e){
            Page<Produit> produits = produitService.getProduitByFavorite(pageNo);

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitByFavorite(pageNo);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-boutique-favorite";
        }
    }

    @GetMapping("/rechercher/liste/{pageNo}")
    public String getBoutiqueByTagName(@RequestParam("nom") String nom,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra, Principal principal){

        Page<Boutique> boutique = boutiqueService.getBoutiqueByTagName(nom,pageNo);


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

        model.addAttribute("boutiques", boutique);
        model.addAttribute("nom", nom);
        model.addAttribute("taille", boutique.getSize());
        model.addAttribute("total", boutique.getTotalPages());
        model.addAttribute("elements", boutique.getTotalElements());
        model.addAttribute("pageCourante", pageNo);
        return "liste-boutique-rechercher";
    }


}
