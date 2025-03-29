package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.CouleurRepository;
import com.soft.MarketPlace.service.*;
import jakarta.servlet.http.HttpServletRequest;
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
import org.thymeleaf.model.IModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.beans.Beans.getInstanceOf;

@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private TypeProduitService typeProduitService;

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private CouleurService couleurService;

    @Autowired
    private TailleService tailleService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private BoutiqueService boutiqueService;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    ClientService clientService;

    @Autowired
    PanierService panierService;

    //retourner la liste des produits de la base de données pour le boutiquier
    @GetMapping("/liste-produits/{pageNo}")
    public String getListeProduits(Model model, Principal principal, RedirectAttributes ra, @PathVariable("pageNo") int pageNo){
        //je vérifie l'utilisateur qui demande la liste des produits et je lui affiche ça
        Utilisateur utilisateur = new Utilisateur();
        Authentication auth = (Authentication) principal;
        if(auth != null && auth.isAuthenticated()){
           utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
            //si lutilisateur nest pas activé on ne lui montre pas cette liste
            if(!utilisateur.getIsValide()){
                model.addAttribute("valide", "veillez renseignez le poste");
                return "/utilisateur/liste-articles";
            }
            Page<Produit> produits = produitService.getProduitByUtilisateur(utilisateur, pageNo);
            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
           return "/utilisateur/liste-articles";
        }else{
            ra.addFlashAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/home";
        }
    }

    // Formulaire d'ajout d'un nouveau produit
    @GetMapping("/creer")
    public String getFormulaireAjoutProduit(Principal principal, @RequestParam("categorie") int idCategorie, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        if(utilisateur == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "/utilisateur/liste-articles";
        }

        try{
            int idCat = idCategorie;
            Categorie categorie2 = new Categorie();
            categorie2 = categorieService.getCategorieById(idCat);
            if(categorie2 == null){
                ra.addFlashAttribute("error","veillez choisir un type de produit !");
                return "redirect:/produit/commencer";
            }else{
                List<TypeProduit> typeProduitList = typeProduitService.getTypeProduitByCategorie(categorie2);
                List<Taille> tailleList = tailleService.getAllTailleByCategorie(categorie2);
                List<Couleurs> couleursList = couleurService.getAllCouleur();
                Produit produit = new Produit();
                model.addAttribute("typeProduits", typeProduitList);
                model.addAttribute("tailles", tailleList);
                model.addAttribute("produit", produit);
                model.addAttribute("couleurs", couleursList);
                model.addAttribute("cate", categorie2);
                return "/utilisateur/form-ajout-produit";
               }
            }catch (Exception e){
            ra.addFlashAttribute("error","une erreur s'est produite !");
            return "redirect:/produit/commencer";
        }


    }

    //retourne la page pour la selection du type de produit a creer

    @GetMapping("/commencer")
    public String getChoixCategorie(Model model, Principal principal){
        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        if(utilisateur == null){
            model.addAttribute("connexion", "veillez vous connecter");
            return "login";
        }

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "/utilisateur/liste-articles";
        }
        List<Categorie> categorieList = categorieService.getAllCategorie();
        model.addAttribute("categories", categorieList);
        return "/utilisateur/choix-categorie-produit";
    }

    //ajout du produit dans la base de données
    @RequestMapping(value = "/creer", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String creerProduit(RedirectAttributes ra, @ModelAttribute("produit") Produit produit,
                               @RequestParam("couleurs") List<Integer> couleurs,
                               @RequestParam("tailles") List<Integer> tailles,
                               @RequestParam("image1") MultipartFile image1,
                               @RequestParam("image2") MultipartFile image2,
                               @RequestParam("image3") MultipartFile image3,
                               @RequestParam("image4") MultipartFile image4,
                               Model model,
                               Principal principal
                               ) throws IOException {


        Produit produit1 = new Produit();
        produit1.setLibelle(produit.getLibelle());
        produit1.setQuantite(produit.getQuantite());
        produit1.setSeuil(produit.getSeuil());
        produit1.setReference(produit.getReference());
        produit1.setPrix(produit.getPrix());
        produit1.setCategorie(produit.getCategorie());
        produit1.setTypeProduit(produit.getTypeProduit());
        produit1.setDescription(produit.getDescription());

        Categorie categorie = new Categorie();
        categorie = categorieService.getCategorieByLibelle(produit1.getCategorie().getLibelle());

        TypeProduit typeProduit = new TypeProduit();
        typeProduit = typeProduitService.getTypeProduitByNom(produit1.getTypeProduit().getNom());

        //commencons par les tailles
        List<Taille> tailleProduit = new ArrayList<Taille>();
        for (int tailleId: tailles) {
            tailleProduit.add(tailleService.getTailleById(tailleId));
        }
        if(tailleProduit.isEmpty()){
            ra.addFlashAttribute("error", "désolé une erreur d'est produite concernant les tailles de votre article");
            return "redirect:/produit/commencer";
        }
        //gérons maintenant les couleurs
        List<Couleurs> couleurProduit = new ArrayList<Couleurs>();
        for (int couleurId: couleurs) {
            couleurProduit.add(couleurService.getCouleursById(couleurId));
        }
        if(couleurProduit.isEmpty()){
            ra.addFlashAttribute("error", "désolé une erreur d'est produite concernant les couleurs de votre article");
            return "redirect:/produit/commencer";
        }

        //gestion des images avant de faire l'ajout
        List<MultipartFile> images = new ArrayList<MultipartFile>();
            images.add(image1);
            images.add(image2);
            images.add(image3);
            images.add(image4);

        //on parcourt les images et on traite

            for (MultipartFile image: images) {
//                String nomImage = StringUtils.cleanPath(image.getOriginalFilename());
//                if(!nomImage.isEmpty() && nomImage.trim() != " "){
//                    if(!nomImage.endsWith(".png") || !nomImage.endsWith(".jpg") || !nomImage.endsWith(".jpeg") || !nomImage.endsWith(".gif")){
//                        System.out.println("voila: "+nomImage);
//                        //List<TypeProduit> typeProduitList = typeProduitService.getTypeProduitByCategorie(produit1.getCategorie());
////                List<Taille> tailleList = tailleService.getAllTaille();
////                List<Couleurs> couleursList = couleurService.getAllCouleur();
////                model.addAttribute("typeProduits", typeProduitService.getTypeProduitByNom(produit1.getTypeProduit().getNom()));
////                model.addAttribute("tailles", tailleList);
////                model.addAttribute("produit", produit1);
////                model.addAttribute("couleurs", couleursList);
////                model.addAttribute("cate", categorieService.getCategorieByLibelle(produit1.getCategorie().getLibelle()));
//                        ra.addFlashAttribute("error", "désolé ce type d'image n'est pas pris en compte extension: "+nomImage);
//                        return "redirect:/home";
//                    }
//
//            } else
                if (image.getSize()>=5000000 && image.getSize()<0) {
//                List<TypeProduit> typeProduitList = typeProduitService.getTypeProduitByCategorie(produit.getCategorie());
//                List<Taille> tailleList = tailleService.getAllTaille();
//                List<Couleurs> couleursList = couleurService.getAllCouleur();
//                model.addAttribute("typeProduits", typeProduitService.getTypeProduitByNom(produit1.getTypeProduit().getNom()));
//                model.addAttribute("tailles", tailleList);
//                model.addAttribute("produit", produit1);
//                model.addAttribute("couleurs", couleursList);
//                model.addAttribute("cate", categorieService.getCategorieByLibelle(produit1.getCategorie().getLibelle()));
                ra.addFlashAttribute("error", "la taille de chaque image doit être inferieure à 4,5 Mo");
                return "redirect:/home";
            }

        }

       // gestion du boutiquier ou utilisateur

        Authentication auth = (Authentication) principal;
        Utilisateur utilisateur = new Utilisateur();
        if(auth != null && auth.isAuthenticated()){
            utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
        }else {
            ra.addFlashAttribute("error", "une erreur s'est produite veillez réesayer !");
            return "redirect:/home";
        }

        //on ajoute le produit maintenant que tout est bon

        Produit produitToAdd = new Produit();
        produitToAdd.setCategorie(categorie);
        produitToAdd.setTypeProduit(typeProduit);
        produitToAdd.setCouleurs(couleurProduit);
        produitToAdd.setDescription(produit.getDescription());
        produitToAdd.setLibelle(produit.getLibelle());
        produitToAdd.setPrix(produit.getPrix());
        produitToAdd.setQuantite(produit.getQuantite());
        produitToAdd.setSeuil(produit.getSeuil());
        produitToAdd.setReference(produit.getReference());
        produitToAdd.setTailles(tailleProduit);
        produitToAdd.setDateAjout(new Date());
        produitToAdd.setUtilisateur(utilisateur);
        produitService.addProduit(produitToAdd);

        //je cree ma liste d'images

        List<Image> imageList = new ArrayList<>();


        for (MultipartFile image: images) {
            String imageChemin = StringUtils.cleanPath(image.getOriginalFilename());
            if(!imageChemin.isEmpty() && imageChemin.trim() != " "){
                System.out.println("ajout de: "+imageChemin);
                File fichier = new File("src/main/resources/static/images/produits/"+imageChemin);
                fichier.createNewFile();
                FileOutputStream fout = new FileOutputStream(fichier);
                fout.write(image.getBytes());
                fout.close();
                Image imageToAdd = new Image();
                imageToAdd.setNom("image"+produit.getLibelle()+""+utilisateur.getNom());
                imageToAdd.setChemin(imageChemin);
                imageToAdd.setProduit(produitToAdd);
                imageService.addImage(imageToAdd);
                imageList.add(imageToAdd);
            }

        }
        ra.addFlashAttribute("success", "le produit a été ajouter à votre boutique!");
        return "redirect:/home";
    }

    //lister tous les produits de la base de données
    @GetMapping("/liste/{pageNo}")
    public String ListeProduits(Model model, @PathVariable("pageNo") int pageNo, Principal principal){
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
        Page<Produit> produits = produitService.pageProduit(pageNo);
        model.addAttribute("produits", produits);
        model.addAttribute("taille", produits.getSize());
        model.addAttribute("total", produits.getTotalPages());
        model.addAttribute("pageCourante", pageNo);
        return "liste-all-produit";
    }

    //retourner la liste des produits les plus vendus
    @GetMapping("/liste/vendus/{pageNo}")
    public String ListeProduitsVendus(Model model, @PathVariable("pageNo") int pageNo, Principal principal){
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
            Page<Produit> produits = produitService.getProduitBySold(pageNo);

            if(produits == null){
                return "liste-all-produit-sold";
            }

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitBySold(0);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-sold";

        }catch (IllegalArgumentException e){
            Page<Produit> produits = produitService.getProduitBySold(0);

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitBySold(0);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-sold";
        }
    }

    //liste des produits par type ou par categorie
    @GetMapping("/liste/{pageNo}/{typeProduit}")
    public String getBoutiqueByTypes(Principal principal, @PathVariable("typeProduit") String typeProduit,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra){
        TypeProduit typeProduit1 = new TypeProduit();
        typeProduit1 = typeProduitService.getTypeProduitByNom(typeProduit);
        Page<Produit> produits = produitService.getProduitByTypeProduit(typeProduit1,pageNo);

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

        model.addAttribute("produits", produits);
        model.addAttribute("typeProduit", typeProduit1.getNom());
        model.addAttribute("taille", produits.getSize());
        model.addAttribute("total", produits.getTotalPages());
        model.addAttribute("elements", produits.getTotalElements());
        model.addAttribute("pageCourante", pageNo);
        return "liste-produit-par-categorie";
    }

    @GetMapping("/details/{id}")
    public String getProductdetails(@PathVariable("id") int idProduit, Model model, Principal principal){
        try{

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

            Produit produit = produitService.getProduitById(idProduit);
            List<Produit> produits = produitService.getByTypeProduit(typeProduitService.getTypeProduitById(produit.getTypeProduit().getIdTypeProduit()));
            model.addAttribute("produit", produit);
            model.addAttribute("produits", produits);
        return "produit-detail";
        }catch (Exception e){
            return "redirect:/produit/liste/0";
        }
    }

    @GetMapping("/modifier/{id}")
    public String getFormulaireUpdateProduit(Principal principal, @PathVariable("id") int idProduit, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        List<Produit> produits = utilisateur.getProduits();
        if(produits == null){
            ra.addFlashAttribute("error","une erreur s'est produite !");
            return "redirect:/home";
        }
        for(Produit p: produits){
            if(p.getIdProduit() == idProduit){
                List<TypeProduit> typeProduitList = typeProduitService.getTypeProduitByCategorie(p.getCategorie());
                List<Taille> tailleList = tailleService.getAllTailleByCategorie(p.getCategorie());
                List<Couleurs> couleursList = couleurService.getAllCouleur();
                model.addAttribute("typeProduits", typeProduitList);
                model.addAttribute("tailles", tailleList);
                model.addAttribute("produit", p);
                model.addAttribute("couleurs", couleursList);
                model.addAttribute("cate", p.getCategorie());
                return "utilisateur/form-modifier-produit";
            }
        }
        ra.addFlashAttribute("error","une erreur s'est produite !");
        return "redirect:/home";
    }

    //modification du produit de l'utilisateur
    @RequestMapping(value = "/modifier", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String modifierProduitUtilisateur(RedirectAttributes ra, @ModelAttribute("produit") Produit produit,
                               @RequestParam("couleurs") List<Integer> couleurs,
                               @RequestParam("tailles") List<Integer> tailles,
                               @RequestParam("typeProduit") int idTypeProduit,
                               @RequestParam("categorie") int idCategorie,
                               @RequestParam("id") int idProduit,
                               Model model,
                               Principal principal
    ) throws IOException {

        // gestion du boutiquier ou utilisateur

        Authentication auth = (Authentication) principal;
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
        if(auth == null || !auth.isAuthenticated() || utilisateur == null){
            ra.addFlashAttribute("error", "une erreur s'est produite veillez réesayer !");
            return "redirect:/home";
        }

        List<Taille> tailleList = new ArrayList<>();
        List<Couleurs> couleursList = new ArrayList<>();

        //on parcour les couleurs et les tailles pour ajouter aux liste avant dajouter au produit
        for(int i: couleurs){
            Couleurs c = couleurService.getCouleursById(i);
            couleursList.add(c);
        }
        if(couleursList == null){
            ra.addFlashAttribute("error", "votre produit doit avoir des couleurs !");
            return "redirect:/home";
        }

        for(int i: tailles){
            Taille t = tailleService.getTailleById(i);
            tailleList.add(t);
        }
        if(tailleList == null){
            ra.addFlashAttribute("error", "votre produit doit avoir des tailles !");
            return "redirect:/home";
        }
        //fin du parcour on peut ajoutter maintenant

        Produit produit1 = produitService.getProduitById(idProduit);
        //on verifie si le produit existe
        if(produit1 == null){
            ra.addFlashAttribute("error", "votre produit n'a pas été retrouvé veillez réessayer!");
            return "redirect:/home";
        }
        produit1.setLibelle(produit.getLibelle());
        produit1.setQuantite(produit.getQuantite());
        produit1.setSeuil(produit.getSeuil());
        produit1.setReference(produit.getReference());
        produit1.setPrix(produit.getPrix());
        produit1.setCategorie(categorieService.getCategorieById(idCategorie));
        produit1.setTypeProduit(typeProduitService.getTypeProduitById(idTypeProduit));
        produit1.setDescription(produit.getDescription());
        produit1.setCouleurs(couleursList);
        produit1.setTailles(tailleList);

        Categorie categorie = new Categorie();
        categorie = categorieService.getCategorieByLibelle(produit1.getCategorie().getLibelle());

        TypeProduit typeProduit = new TypeProduit();
        typeProduit = typeProduitService.getTypeProduitByNom(produit1.getTypeProduit().getNom());

        //on ajoute le produit maintenant que tout est bon

        Produit produitToAdd = produitService.getProduitById(produit1.getIdProduit());
        produitToAdd.setCategorie(categorie);
        produitToAdd.setTypeProduit(typeProduit);
        produitToAdd.setCouleurs(produit1.getCouleurs());
        produitToAdd.setDescription(produit.getDescription());
        produitToAdd.setLibelle(produit.getLibelle());
        produitToAdd.setPrix(produit.getPrix());
        produitToAdd.setQuantite(produit.getQuantite());
        produitToAdd.setSeuil(produit.getSeuil());
        produitToAdd.setReference(produit.getReference());
        produitToAdd.setTailles(produit1.getTailles());

        produitService.addProduit(produitToAdd);

        ra.addFlashAttribute("success", "le produit a été modifié avec success!");
        return "redirect:/home";
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerProduit(@PathVariable("id") int idProduit, Model model, Principal principal, RedirectAttributes ra, HttpServletRequest request){

        // gestion du boutiquier ou utilisateur

        Authentication auth = (Authentication) principal;
        Utilisateur utilisateur = new Utilisateur();
        if(auth != null && auth.isAuthenticated()){
            utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
        }else {
            ra.addFlashAttribute("error", "une erreur s'est produite veillez réesayer !");
            return "redirect:/home";
        }
        Produit produit = produitService.getProduitById(idProduit);
        if(produit == null){
            ra.addFlashAttribute("refus", "votre produit n'a pas été trouvé");
            return "redirect:" + request.getHeader("Referer");
        }else{
            //si le produit est commandé on ne le supprime pas
            List<LigneCommandes> ligneCommandes =  produit.getLigneCommandes();
            if(ligneCommandes != null){
                for (LigneCommandes lc : ligneCommandes){
                    if(lc.getCommande().getEtat().equals("en attente")){
                        ra.addFlashAttribute("refus", "votre produit appartient a une commande non livrée");
                        return "redirect:" + request.getHeader("Referer");
                    }
                }
            }

            //recuperation de la liste des images et suppression
            List<Image> imageList = produit.getImages();
            if(imageList != null){
                for(Image i: imageList){
                    File fichier = new File("src/main/resources/static/images/produits/"+i.getChemin());
                    fichier.deleteOnExit();
                }
            }

            //suppression du produit
            produitService.deleteProduit(produit.getIdProduit());
            ra.addFlashAttribute("success", "votre produit a bien été supprimé");
        }
        return "redirect:/home";
    }

    //liste des produits ou articles les mieux notés
    @GetMapping("/liste/notes/{pageNo}")
    public String ListeProduitsNoter(Model model, @PathVariable("pageNo") int pageNo, Principal principal){
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
            Page<Produit> produits = produitService.getProduitByFavorite(pageNo);

            if(produits == null){
                return "liste-all-produit-favorite";
            }

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitByFavorite(pageNo);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-favorite";

        }catch (IllegalArgumentException e){
            Page<Produit> produits = produitService.getProduitByFavorite(pageNo);

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitByFavorite(pageNo);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-favorite";
        }
    }
    
    //afficher les favories d'un client
    @GetMapping("/liste/favorie/{pageNo}")
    public String ListeProduitsFavorie(Model model, @PathVariable("pageNo") int pageNo, Principal principal, RedirectAttributes ra){
        //traitement du menu
        int taille = 0;
        Authentication auth = (Authentication) principal;
        Client client = new Client();
        if(principal != null){
            String userName = principal.getName();
            client = clientService.getClientByEmail(userName);
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
            if(principal == null){
                ra.addFlashAttribute("connexion", "veilez vous connecter");
                return "redirect:/login";
            }
            Page<Produit> produits = produitService.getProduitByFavoriteClient(pageNo, client);

            if(produits == null){
                return "liste-all-produit-favorite-client";
            }

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitByFavoriteClient(pageNo, client);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-favorite-client";

        }catch (IllegalArgumentException e){
            Page<Produit> produits = produitService.getProduitByFavoriteClient(pageNo, client);

            if(pageNo > produits.getTotalPages()){
                produits = produitService.getProduitByFavoriteClient(pageNo, client);
            }

            model.addAttribute("produits", produits);
            model.addAttribute("taille", produits.getSize());
            model.addAttribute("total", produits.getTotalPages());
            model.addAttribute("pageCourante", pageNo);
            return "liste-all-produit-favorite-client";
        }
    }

    //liste des produits recherchés
    @GetMapping("/recherche/liste/{pageNo}")
    public String getProduitByLibelle(Principal principal, @RequestParam("libelle") String libelle,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra){

        Page<Produit> produits = produitService.getProduitByTagName(libelle,pageNo);

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

        model.addAttribute("produits", produits);
        model.addAttribute("libelle", libelle);
        model.addAttribute("taille", produits.getSize());
        model.addAttribute("total", produits.getTotalPages());
        model.addAttribute("elements", produits.getTotalElements());
        model.addAttribute("pageCourante", pageNo);
        return "liste-produit-rechercher";
    }

    @GetMapping("/rechercher/boutique/liste/{pageNo}")
    public String getProduitBoutiqueByLibelle(Principal principal, @RequestParam("libelle") String libelle, @RequestParam("boutique") int idBoutique,@PathVariable("pageNo") int pageNo, Model model, RedirectAttributes ra, HttpServletRequest request){

        Page<Produit> produits = produitService.getProduitBoutiqueByTagName(idBoutique,libelle,pageNo);
        Boutique boutique = boutiqueService.getBoutiqueById(idBoutique);

        if(boutique == null){
            return "redirect:" + request.getHeader("Referer");

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

        model.addAttribute("produits", produits);
        model.addAttribute("libelle", libelle);
        model.addAttribute("idBoutique", idBoutique);
        model.addAttribute("boutique", boutique);
        model.addAttribute("taille", produits.getSize());
        model.addAttribute("total", produits.getTotalElements());
        model.addAttribute("elements", produits.getTotalPages());
        model.addAttribute("pageCourante", pageNo);
        return "liste-produit-rechercher-par-boutique";
    }

}
