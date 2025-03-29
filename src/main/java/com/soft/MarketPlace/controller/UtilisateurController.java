package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.UserRepository;
import com.soft.MarketPlace.repository.UtilisateursRepository;
import com.soft.MarketPlace.service.*;
import com.soft.MarketPlace.serviceImplement.ApiService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    // toute les methodes du Utilisateur
    //declaration de la variable pour les mails
    ApiService api = new ApiService();

    @Autowired
    UtilisateursRepository UtilisateursRepository;
    //mes message pour les mails
    public String messageToSend(int code) {
        return "Bonjour monsieur/madame nous vous envoyons ce mail"
                + " parceque vous venez de vous inscrire à l'application"
                + " SOFT MARKET.</br>"
                + " afin de poursuivre votre inscription vous devez soumettre le code <h3> "
                + code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
                + " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
                + " au +237698765588 à bientot.";
    }
    public String messageToSend2(int code) {
        return "Bonjour monsieur/madame nous vous envoyons ce mail"
                + " parceque vous venez de faire une demande de modification de mot de passe pour "
                + " SOFT MARKET.</br>"
                + " afin de poursuivre votre opération, vous devez soumettre le code <h3> "
                + code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
                + " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
                + " au +237698765588 à bientot.";
    }

    public String messageToSend3(String boutique, String adresse, String numero1, String numero2, String poste) {
        return "Bonjour Monsieur/Madame nous vous envoyons ce mail"
                + " parceque la boutique"
                + boutique+" situé a "+adresse+" et repondant au numéro "
                + numero1 + " ou au "+ numero2 + " recrute du personnel pour le poste de/d'.</br>"
                +poste+ " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
                + " au +237698765588 à bientot.";
    }
    //variable pour le nombre aleatoire
    int nombre1 = 0;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    CommandeService commandeService;

    @Autowired
    AdresseService adresseService;

    @Autowired
    ClientService clientService;

    //pour chiffrer le password
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //formulaire inscription de lutilisateur
    @GetMapping("/inscription")
    public String formRegistration(Model model) {
        model.addAttribute("utilisateur",new Utilisateur());
        return "form-inscription-utilisateur";
    }

    //methode qui envoie le mail a l'utilisateur a l'inscription
    @PostMapping("/inscription/confirm")
    public String confirmMail(@ModelAttribute("utilisateur") Utilisateur utilisateur, Model model, RedirectAttributes ra) throws AddressException, MessagingException {
        if(utilisateur.getEmail() != null) {
            nombre1 = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
            System.out.println("nombre généré: "+nombre1);
            int nombre = nombre1+1024;
            api.sendmail(utilisateur.getEmail(), messageToSend(nombre1).indent(1), "Inscription");
            System.out.println("avant: "+utilisateur.toString());
            model.addAttribute("User", utilisateur);
            model.addAttribute("nombre", nombre);
            return "form-inscription-utilisateur-2";
        }else {
            System.out.println("mail: "+utilisateur.getEmail());
            ra.addAttribute("error", "veillez vous assurer que l'e-mail est valide");
            return "form-inscription-utilisateur";
        }
    }

    //inscription proprement dite
    @PostMapping("/inscription")
    public String UtilisateurRegistration(@RequestParam("number") String number, @RequestParam("nombre") String nombre, @ModelAttribute("utilisateur") Utilisateur utilisateur, Model ra) throws Exception, Exception {
        List<Utilisateur> allUtilisateur = utilisateurService.getAllUtilisateur();
        int nombre1 = Integer.parseInt(nombre.replace(",", ""));
        int number1 = Integer.parseInt(number.replace(",", ""));
        String email = utilisateur.getEmail();
        String exist = null;
        for(Utilisateur c: allUtilisateur) {
            if(c.getEmail().equalsIgnoreCase(utilisateur.getEmail()))
                exist = email;
        }
        if(exist == null) {
            if(!utilisateur.getConfirmPassword().equals(utilisateur.getPassword())) {
                ra.addAttribute("utilisateur", utilisateur);
                ra.addAttribute("nombre", nombre.replace(",", ""));
                ra.addAttribute("confirm", "la confirmation du mot de passe est incorrecte !");
                return "form-inscription-utilisateur-2";
            }
            else {
                if(number1 == nombre1 - 1024) {
                    Role role = roleService.getRoleByNom("utilisateur");
                    List<Role> roleList = new ArrayList<>();
                    if(role != null){
                        roleList.add(role);
                    }else{
                        return "form-inscription-utilisateur";
                    }
                    //User user = new User();
                    utilisateur.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
                    utilisateur.setConfirmPassword(bCryptPasswordEncoder.encode(utilisateur.getConfirmPassword()));
                    utilisateur.setIsTelephone1Valide(false);
                    utilisateur.setIsTelephone2Valide(false);
                    utilisateur.setChiffreAffaire(0);
                    utilisateur.setIsValide(false);
                    System.out.println("apres: "+utilisateur.toString());
                    //user.createUserFromUtilisateur(Utilisateur);
                    //user.setRoles(roleList);
                    //userRepository.save(user);
                    //Utilisateur.setUser(user);
                    utilisateur.setRoles(roleList);
                    utilisateurService.addUtilisateur(utilisateur);
                    ra.addAttribute("success", "vous avez bien été enregistré");
                    return "/login";
                }
                ra.addAttribute("utilisateur", utilisateur);
                ra.addAttribute("nombre", nombre.replace(",", ""));
                ra.addAttribute("erreur", "le code de vérification est incorrect ");
                return "form-inscription-utilisateur-2";
            }

        }else {
            ra.addAttribute("utilisateur", utilisateur);
            ra.addAttribute("exist", "l'utilisateur avec cette addresse existe déjà !");
            return "form-inscription-utilisateur-2";
        }
    }

    //formulaire pour la recuperation du mot de passe
    @PostMapping("/form/password")
    public String formrecuverPassword(Utilisateur utilisateur, @RequestParam("id") String nombre, @RequestParam("number") String number, Model model, RedirectAttributes ra) {
        int nombre2 = Integer.parseInt(nombre.replace(",", ""));
        int number1 = Integer.parseInt(number.replace(",", ""));
        System.out.println("nombre: " + nombre2 + " number: " + number1);
        if(utilisateur.getEmail() != null) {
            if(utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()) != null) {
                System.out.println("verification des nombres");
                if(number1 == nombre2 - 1024) {
                    model.addAttribute("utilisateur", utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()));
                    return "utilisateur/formPassword";
                }else {
                    model.addAttribute("nombre", nombre.replace(",", ""));
                    model.addAttribute("code", "le code de vérification est incorrect ");
                    model.addAttribute("utilisateur", utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()));
                    return "utilisateur/recuperation-password";
                }
            }else {
                ra.addFlashAttribute("error", "l'utilisateur est introuvable");
                return "redirect:/login";
            }
        }else {
            ra.addFlashAttribute("error", "l'utilisateur est introuvable");
            return "redirect:/login";
        }
    }

    //fonction de recuperation du mot de passe
    @PostMapping("/recuperer")
    public String passwordRecuver(@ModelAttribute("utilisateur") Utilisateur utilisateur, Model model, RedirectAttributes ra) {
        if(utilisateurService.getUtilisateurById(utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()).getIdUser()) != null) {
            System.out.println("id a modifier: "+utilisateurService.getUtilisateurById(utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()).getIdUser()).getIdUser());
            if(utilisateur.getPassword().equals(utilisateur.getConfirmPassword())) {
                System.out.println("mot de passe a modifier: "+utilisateur.getPassword());
                Utilisateur u = utilisateurService.getUtilisateurById(utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()).getIdUser());
                u.setPassword(bCryptPasswordEncoder.encode(utilisateur.getPassword()));
                u.setConfirmPassword(bCryptPasswordEncoder.encode(utilisateur.getConfirmPassword()));
                utilisateurService.addUtilisateur(u);
                ra.addFlashAttribute("sucess", "votre mot de passe a bien été modifié");
                return "redirect:/login";
            }else {
                model.addAttribute("error", "la confirmation du mot de passe est incorrecte !");
                model.addAttribute("utilisateur", utilisateurService.getUtilisateurByEmail(utilisateur.getEmail()));
                return "utilisateur/formPassword";
            }
        }else {
            ra.addFlashAttribute("notFound", "l'utilisateur avec cette adresse mail n'a pas de compte");
            return "redirect:/compte/password/recuver";
        }
    }
//retourne les commandes en attente de l'utilisateur
    @GetMapping("/commande/attente")
    public String getCommandeUtilisateurByEtat(Principal principal, Model model){

        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        List<Commande> commandeAttente = commandeService.getCommandeUtilisateurByEtat(utilisateur, "en attente");

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "utilisateur/commande-en-attente";
        }

        model.addAttribute("commandes", commandeAttente);

        return "utilisateur/commande-en-attente";
    }

    //retourne les paiements en attente de l'utilisateur
    @GetMapping("/paiement/attente")
    public String getPaiementUtilisateurByEtat(Principal principal, Model model){

        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        List<Commande> commandeAttente = commandeService.getCommandeUtilisateurByEtat(utilisateur, "en attente");

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "utilisateur/paiement-en-attente";
        }

        model.addAttribute("commandes", commandeAttente);

        return "utilisateur/paiement-en-attente";
    }


    @GetMapping("/commande/details/{id}")
    public String DetailCommande(Principal principal, Model model, @PathVariable("id") int idCommande){

        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }
        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        List<Commande> commandes = utilisateur.getCommandes();

        Commande commande = new Commande();

        for(Commande c: commandes){
            if(c.getIdCommande() == idCommande){
                commande = c;
            }
        }

        List<LigneCommandes> ligneCommandes = new ArrayList<>();
        if(commande != null){
            for(LigneCommandes lc:commande.getLigneCommandes()){
                ligneCommandes.add(lc);
            }
        }else{
            return "redirect:/home";
        }

        //si lutilisateur nest pas activé on ne lui montre pas cette page
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "utilisateur/commande-details";
        }

        if(!ligneCommandes.isEmpty() && ligneCommandes != null){
            model.addAttribute("lignesCommandes", ligneCommandes);
            return "utilisateur/commande-details";
        }
        return "redirect:/home";
    }

//liste de toutes les commandes livrées
    @GetMapping("/commande/livrer")
    public String getCommandeUtilisateurByEtatLivrer(Principal principal, Model model){

        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        List<Commande> commandeLivrer = commandeService.getCommandeUtilisateurByEtat(utilisateur, "livrer");

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "utilisateur/commande-livrer";
        }

        model.addAttribute("commandes", commandeLivrer);

        return "utilisateur/commande-livrer";
    }
    //liste de tout les paiements recu par l'utilisateur
    @GetMapping("/paiement/recu")
    public String getPaiementUtilisateurByEtatRecu(Principal principal, Model model){

        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        List<Commande> commandeLivrer = commandeService.getCommandeUtilisateurByEtat(utilisateur, "livrer");

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            model.addAttribute("valide", "veillez renseignez le poste");
            return "utilisateur/paiement-recu";
        }

        model.addAttribute("commandes", commandeLivrer);

        return "utilisateur/paiement-recu";
    }

    //on valide que la livraison a bien été effectuée avant le paiement
    @PostMapping("/commande/livraison/valider")
    public String validerLivraisonCommande(
            @RequestParam("client") String emailClient,
            @RequestParam("commande") int idCommande,
            @RequestParam("code") String code,
            Principal principal,
            RedirectAttributes ra,
            Model model
    ){
        if(principal == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }

        String userName = principal.getName();
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);
        if(utilisateur == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/home";
        }

        Client client = clientService.getClientByEmail(emailClient);
        if(client == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/home";
        }

        Commande commande = commandeService.getCommandeById(idCommande);
        if(commande == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/home";
        }

        Commande commande1 = commandeService.validerCommande(commande,code,utilisateur,client);
        if(commande1 == null){
            ra.addFlashAttribute("error", "une erreur s'est produite");
            return "redirect:/home";
        }else{
            model.addAttribute("valider", "la commande a bien été livrée");
            return "redirect:/home";
        }
    }

    @GetMapping("/commande/liste-commandes")
    public String getListeCommandes(Model model, Principal principal, RedirectAttributes ra){
        //je vérifie l'utilisateur qui demande la liste des commandes et je lui affiche ca
        Utilisateur utilisateur = new Utilisateur();
        Authentication auth = (Authentication) principal;
        if(auth == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }
        if(auth != null && auth.isAuthenticated()){
            utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
            List<Commande> commandes = new ArrayList<Commande>();
            commandes = commandeService.getCommandeByUtilisateur(utilisateur);
            //si lutilisateur nest pas activé on ne lui montre pas cette liste
            if(!utilisateur.getIsValide()){
                model.addAttribute("valide", "veillez renseignez le poste");
                return "/utilisateur/historique-commandes";
            }
            model.addAttribute("commandes", commandes);
            return "/utilisateur/historique-commandes";
        }else{
            ra.addFlashAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/home";
        }
    }

    @GetMapping("/paiement/liste-paiements")
    public String getListePaiements(Model model, Principal principal, RedirectAttributes ra){
        //je verifie l'utilisateur qui demande la liste des paiements et je lui affiche ca
        Utilisateur utilisateur = new Utilisateur();
        Authentication auth = (Authentication) principal;
        if(auth == null){
            model.addAttribute("error", "veillez vous connectez");
            return "login";
        }
        if(auth != null && auth.isAuthenticated()){
            utilisateur = utilisateurService.getUtilisateurByEmail(auth.getName());
            List<Commande> commandes = new ArrayList<Commande>();
            commandes = commandeService.getCommandeByUtilisateur(utilisateur);
            //si lutilisateur nest pas activé on ne lui montre pas cette liste
            if(!utilisateur.getIsValide()){
                model.addAttribute("valide", "veillez renseignez le poste");
                return "/utilisateur/historique-paiements";
            }
            model.addAttribute("commandes", commandes);
            return "/utilisateur/historique-paiements";
        }else{
            ra.addFlashAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/home";
        }
    }

    @PostMapping("/recrute")
    public String recrutement(@RequestParam("poste") String poste, Principal principal, Model model, RedirectAttributes ra) throws MessagingException {

        //je verifie l'utilisateur qui demande le recrutement
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurService.getUtilisateurByEmail(principal.getName());
        Authentication auth = (Authentication) principal;
        if(auth == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        //si lutilisateur nest pas activé on ne lui montre pas cette liste
        if(!utilisateur.getIsValide()){
            ra.addFlashAttribute("valide", "veillez renseignez le poste");
            return "redirect:/home";
        }

        if(poste == null){
            ra.addFlashAttribute("vide", "veillez renseignez le poste");
            return "redirect:/home";
        }
        Boutique  boutique = utilisateur.getBoutique();
        if(boutique == null){
            ra.addFlashAttribute("error", "veillez renseignez le poste");
            return "redirect:/home";
        }
        if(auth != null && auth.isAuthenticated()){
            if(utilisateur == null){
                ra.addFlashAttribute("error", "utilisateur non valide");
                return "redirect:/home";
            }else{
                api.sendmail(
                        "brayantmohamed@gmail.com",
                        messageToSend3(
                                boutique.getNom(),
                                boutique.getEmplacement(),
                                utilisateur.getTelephone1(),
                                utilisateur.getTelephone2(),
                                poste),
                        "avis de recrutement"
                        );
            }
            ra.addFlashAttribute("recrute", "un avis de recrutement sera lancé pour la boutique "+boutique.getNom());
            return "redirect:/home";
        }else{
            ra.addFlashAttribute("error", "désolé une erreur s'est produite");
            return "redirect:/home";
        }
    }

    //retourner la page de gestion de compte de lutilisateur
    @GetMapping("/compte")
    public String compteUtilisateur(Principal principal, Model model){
        //je vérifie l'utilisateur qui demande le recrutement
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurService.getUtilisateurByEmail(principal.getName());
        List<Adresse> adresses = adresseService.getAllAdresse();
        Authentication auth = (Authentication) principal;
        if(auth == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        if(utilisateur == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        String nomPrenom = utilisateur.getPrenom() + " " +utilisateur.getNom();
        //on verifie ses données
        if(utilisateur.getAdresseUser() == null || utilisateur.getAdresseUser().isEmpty() ){
            model.addAttribute("adresseVide", "adresses vides");
            model.addAttribute("adresses", adresses);
        }
        if(!utilisateur.getIsTelephone1Valide()){
            model.addAttribute("telephone1", "votre premier numéro de transaction n'a pas encore été confirmé veillez vous rendre dans la session compte");
        }
        if(!utilisateur.getIsTelephone2Valide()){
            model.addAttribute("telephone2", "votre second numéro de transaction n'a pas encore été confirmé veillez vous rendre dans la session compte");
        }

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("utilisateurInformations", nomPrenom);
        return "utilisateur/compte";

    }

    //verification de l'utilisateur
    //modifier les informations personnelles d'un utilisateur
    @PostMapping("/modifier")
    public String modifierClient(Model model, @ModelAttribute("utilisateur") Utilisateur utilisateur, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        String userName = principal.getName();
        //modification des informations

        Utilisateur utilisateur1 = utilisateurService.getUtilisateurByEmail(userName);

        Utilisateur utilisateurToUpdate = utilisateurService.getUtilisateurById(utilisateur1.getIdUser());

        if(utilisateur.getTelephone1() != utilisateur1.getTelephone1()){
            utilisateurToUpdate.setIsValide(false);
            utilisateurToUpdate.setIsTelephone1Valide(false);
            utilisateurToUpdate.setTelephone1(utilisateur.getTelephone1());
        }

        if(utilisateur.getTelephone2() != utilisateur1.getTelephone2()){
            utilisateurToUpdate.setIsValide(false);
            utilisateurToUpdate.setIsTelephone2Valide(false);
            utilisateurToUpdate.setTelephone2(utilisateur.getTelephone2());
        }

        utilisateurToUpdate.setNom(utilisateur.getNom());
        utilisateurToUpdate.setPrenom(utilisateur.getPrenom());
        utilisateurToUpdate.setDateNaissance(utilisateur.getDateNaissance());
        utilisateurService.addUtilisateur(utilisateurToUpdate);

        //fin modification des information

        ra.addFlashAttribute("success", "informations modifiées avec sucess");
        return "redirect:/utilisateur/compte";
    }


    //modifier les informations personnelles d'un client
    @PostMapping("/modifier/password")
    public String modifierPasswordClient(RedirectAttributes ra,Model model,@RequestParam("nouveau") String nouveau,@RequestParam("confirmer") String confirmer,  Principal principal){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        //modification des informations

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        Utilisateur utilisateurToUpdate = utilisateurService.getUtilisateurById(utilisateur.getIdUser());

        String passwordNouveau = bCryptPasswordEncoder.encode(nouveau.trim());

        String passwordconfirmer = bCryptPasswordEncoder.encode(confirmer.trim());

        if(!nouveau.trim().equals(confirmer)){
            ra.addFlashAttribute("error", "la confirmation n'est pas identique au nouveau mot de passe");
            return "redirect:/utilisateur/compte";
        }

        utilisateurToUpdate.setPassword(passwordNouveau);
        utilisateurToUpdate.setConfirmPassword(passwordconfirmer);
        utilisateurService.addUtilisateur(utilisateurToUpdate);

        //fin modification des information

        ra.addFlashAttribute("success", "informations modifiées avec sucess");
        return "redirect:/utilisateur/compte";
    }


    //verifier le numéro de telephone 1
    @GetMapping("/activer/telephone1")
    public String verifierNumero1(Principal principal, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        if(!utilisateur.getIsTelephone1Valide()){
            Utilisateur utilisateurToUp = utilisateurService.getUtilisateurById(utilisateur.getIdUser());
            utilisateurToUp.setIsTelephone1Valide(true);
            if(utilisateur.getIsTelephone2Valide()){
                utilisateurToUp.setIsValide(true);
                utilisateurService.addUtilisateur(utilisateurToUp);
            }else{
                utilisateurService.addUtilisateur(utilisateurToUp);
            }
            ra.addFlashAttribute("success", "le numero a bien été validé");
            return "redirect:/utilisateur/compte";
        }else {
            ra.addFlashAttribute("error", "ce numéro est déja activé");
            return "redirect:/utilisateur/compte";
        }

    }

    //verifier le numero de telephone 2
    @GetMapping("/activer/telephone2")
    public String verifierNumero2(Principal principal, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();

        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(userName);

        if(!utilisateur.getIsTelephone2Valide()){
            Utilisateur utilisateurToUp = utilisateurService.getUtilisateurById(utilisateur.getIdUser());
            utilisateurToUp.setIsTelephone2Valide(true);
            if(utilisateur.getIsTelephone1Valide()){
                utilisateurToUp.setIsValide(true);
                utilisateurService.addUtilisateur(utilisateurToUp);
            }else{
                utilisateurService.addUtilisateur(utilisateurToUp);
            }
            ra.addFlashAttribute("success", "vos numéros sont valides");
            return "redirect:/utilisateur/compte";
        }else {
            ra.addFlashAttribute("error", "ce numéro est déja activé");
            return "redirect:/utilisateur/compte";
        }

    }

}

