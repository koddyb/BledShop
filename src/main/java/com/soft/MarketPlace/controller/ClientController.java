package com.soft.MarketPlace.controller;

import com.soft.MarketPlace.entity.Adresse;
import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Commande;
import com.soft.MarketPlace.entity.Role;
import com.soft.MarketPlace.repository.ClientsRepository;
import com.soft.MarketPlace.repository.UserRepository;
import com.soft.MarketPlace.service.*;
import com.soft.MarketPlace.serviceImplement.ApiService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/client")
public class ClientController {

    // toute les methodes du client
    //declaration de la variable pour les mails
    ApiService api = new ApiService();

    @Autowired
    ClientsRepository clientsRepository;


    //mes message pour les mails
    public String messageToSend(int code) {
        return "Bonjour monsieur/madame nous vous envoyons ce mail"
                + " parceque vous venez de vous inscrire à l'application"
                + " SOFT MARKET.</br>"
                + " afin de poursuivre votre inscription vous devez soumettre le code <h3> "
                + code +"</h3> dans le site pour procéder à la vérification de votre adresse</br>"
                + " merci pour votre compréhension n'hésitez pas a soumettre vos questions"
                + " au +237698765588 et à bientot.";
    }
    //variable pour le nombre aleatoire
    int nombre1 = 0;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    CommandeService commandeService;

    @Autowired
    ClientService clientService;

    @Autowired
    AdresseService adresseService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;


    //pour chiffrer le password
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/inscription")
    public String formRegistration(Model model) {
        model.addAttribute("client",new Client());
        return "form-inscription-client";
    }

    //methode qui envoie le mail a l'utilisateur a l'inscription
    @PostMapping("/inscription/confirm")
    public String confirmMail(@ModelAttribute("client") Client client, Model model, RedirectAttributes ra) throws AddressException, MessagingException {
        if(client.getEmail() != null) {
            nombre1 = (int) new Random().nextInt(1000000 - 100000 + 1) + 100000;
            System.out.println("nombre généré: "+nombre1);
            int nombre = nombre1+1024;
            api.sendmail(client.getEmail(), messageToSend(nombre1).indent(1), "Inscription");
            System.out.println("avant: "+client.toString());
            model.addAttribute("client", client);
            model.addAttribute("nombre", nombre);
            return "form-inscription-client-2";
        }else {
            System.out.println("mail: "+client.getEmail());
            ra.addFlashAttribute("error", "veillez vous assurer que l'e-mail est valide");
            return "redirect:/client/inscription";
        }
    }

    @PostMapping("/inscription")
    public String clientRegistration(@RequestParam("number") String number, @RequestParam("nombre") String nombre, @ModelAttribute("client") Client client, Model ra) throws Exception, Exception {
        List<Client> allClient = clientService.getAllClient();
        int nombre1 = Integer.parseInt(nombre.replace(",", ""));
        int number1 = Integer.parseInt(number.replace(",", ""));
        String email = client.getEmail();
        String exist = null;
        for(Client c: allClient) {
            if(c.getEmail().equalsIgnoreCase(client.getEmail()))
                exist = email;
        }
        if(exist == null) {
            if(!client.getConfirmPassword().equals(client.getPassword())) {
                ra.addAttribute("client", client);
                ra.addAttribute("nombre", nombre.replace(",", ""));
                ra.addAttribute("confirm", "la confirmation du mot de passe est incorrecte !");
                return "form-inscription-client-2";
            }
            else {
                if(number1 == nombre1 - 1024) {
                    Role role = roleService.getRoleByNom("CLIENT");
                    List<Role> roleList = new ArrayList<>();
                    if(role != null){
                        roleList.add(role);
                    }else{
                        ra.addAttribute("error-role", "désolé une erreur s'est produite");
                        return "form-inscription-client";
                    }
                    //User user = new User();
                    client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
                    client.setConfirmPassword(bCryptPasswordEncoder.encode(client.getConfirmPassword()));
                    client.setIsTelephone1Valide(false);
                    client.setIsTelephone2Valide(false);
                    client.setIsValide(false);
                    System.out.println("apres: "+client.toString());
                    //user.createUserFromClient(client);
                    //user.setRoles(roleList);
                    //userRepository.save(user);
                    //client.setUser(user);
                    client.setRoles(roleList);
                    clientService.addClient(client);
                    ra.addAttribute("success", "vous avez bien été enregistré");
                    return "/login";
                }
                ra.addAttribute("client", client);
                ra.addAttribute("nombre", nombre.replace(",", ""));
                ra.addAttribute("erreur", "le code de vérification est incorrect ");
                return "form-inscription-client-2";
            }

        }else {
            ra.addAttribute("client", client);
            ra.addAttribute("exist", "l'utilisateur avec cette addresse existe déjà !");
            return "form-inscription-client-2";
        }

    }

    @GetMapping("/verifier")
    public String verifierCompte(Principal principal, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        String userName = principal.getName();
        Client client = clientService.getClientByEmail(userName);
        if(!client.getIsValide()){
            return "verification";
        }else{
            model.addAttribute("success", "vos informations sont bien vérifiées");
            return "verification";
        }

    }

    //modifier les informations personnelles d'un client
    @PostMapping("/modifier")
    public String modifierClient(Model model, @ModelAttribute("client") Client client, Principal principal, RedirectAttributes ra){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }
        String userName = principal.getName();
        //modification des informations

        Client client1 = clientService.getClientByEmail(userName);

        Client clientToUpdate = clientService.getClientById(client1.getIdUser());

        if(client.getTelephone1() != client1.getTelephone1()){
            clientToUpdate.setIsValide(false);
            clientToUpdate.setIsTelephone1Valide(false);
            clientToUpdate.setTelephone1(client.getTelephone1());
        }

        if(client.getTelephone2() != client1.getTelephone2()){
            clientToUpdate.setIsValide(false);
            clientToUpdate.setIsTelephone2Valide(false);
            clientToUpdate.setTelephone2(client.getTelephone2());
        }

        clientToUpdate.setNom(client.getNom());
        clientToUpdate.setPrenom(client.getPrenom());
        clientToUpdate.setDateNaissance(client.getDateNaissance());
        clientService.addClient(clientToUpdate);

        //fin modification des information

        ra.addFlashAttribute("success", "informations modifiées avec sucess");
        return "redirect:/compte";
    }


    //modifier les informations personnelles dun client
    @PostMapping("/modifier/password")
    public String modifierPasswordClient(RedirectAttributes ra,Model model,@RequestParam("nouveau") String nouveau,@RequestParam("confirmer") String confirmer,  Principal principal){

        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();
        //modification des informations

        Client client = clientService.getClientByEmail(userName);

        Client clientToUpdate = clientService.getClientById(client.getIdUser());

        String passwordNouveau = bCryptPasswordEncoder.encode(nouveau.trim());

        String passwordconfirmer = bCryptPasswordEncoder.encode(confirmer.trim());

        if(!nouveau.trim().equals(confirmer)){
            ra.addFlashAttribute("error", "la confirmation n'est pas identique au nouveau mot de passe");
            return "redirect:/compte";
        }

        clientToUpdate.setPassword(passwordNouveau);
        clientToUpdate.setConfirmPassword(passwordconfirmer);
        clientService.addClient(clientToUpdate);

        //fin modification des information

        model.addAttribute("success", "informations modifiées avec sucess");
        return "redirect:/compte";
    }


    //verifier le numero de telephone 1
    @GetMapping("/telephone1")
    public String verifierNumero1(Principal principal, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();

        Client client = clientService.getClientByEmail(userName);

        if(!client.getIsTelephone1Valide()){
            Client clientToUp = clientService.getClientById(client.getIdUser());
            clientToUp.setIsTelephone1Valide(true);
            if(client.getIsTelephone2Valide()){
                clientToUp.setIsValide(true);
                clientService.addClient(clientToUp);
            }else{
                clientService.addClient(clientToUp);
            }
            return "redirect:/compte";
        }else {
            ra.addFlashAttribute("error", "ce numéro est déja activé");
            return "redirect:/compte";
        }

    }

    //verifier le numero de telephone 2
    @GetMapping("/telephone2")
    public String verifierNumero2(Principal principal, Model model, RedirectAttributes ra){
        if(principal == null){
            model.addAttribute("connexion", "vous devez vous connecter");
            return "login";
        }

        String userName = principal.getName();

        Client client = clientService.getClientByEmail(userName);

        if(!client.getIsTelephone2Valide()){
            Client clientToUp = clientService.getClientById(client.getIdUser());
            clientToUp.setIsTelephone2Valide(true);
            if(client.getIsTelephone1Valide()){
                clientToUp.setIsValide(true);
                clientService.addClient(clientToUp);
            }else{
                clientService.addClient(clientToUp);
            }
            return "redirect:/compte";
        }else {
            ra.addFlashAttribute("error", "ce numéro est déja activé");
            return "redirect:/compte";
        }

    }

    @PostMapping("/form/password")
    public String formrecuverPassword(Client client, @RequestParam("id") String nombre, @RequestParam("number") String number,Model model, RedirectAttributes ra) {
        int nombre2 = Integer.parseInt(nombre.replace(",", ""));
        int number1 = Integer.parseInt(number.replace(",", ""));
        System.out.println("nombre: " + nombre2 + " number: " + number1);
        if(client.getEmail() != null) {
            if(clientService.getClientByEmail(client.getEmail()) != null) {
                System.out.println("verification des nombres");
                if(number1 == nombre2 - 1024) {
                    model.addAttribute("client", clientService.getClientByEmail(client.getEmail()));
                    return "client/formPassword";
                }else {
                    model.addAttribute("nombre", nombre.replace(",", ""));
                    model.addAttribute("code", "le code de vérification est incorrect ");
                    model.addAttribute("client", clientService.getClientByEmail(client.getEmail()));
                    return "client/recuperation-password";
                }
            }else {
                model.addAttribute("connexion", "vous devez vous connecter");
                return "redirect:/login";
            }
        }else {
            model.addAttribute("connexion", "vous devez vous connecter");
            return "redirect:/login";
        }
    }

    @PostMapping("/recuperer")
    public String passwordRecuver(@ModelAttribute("client") Client client, Model model, RedirectAttributes ra) {
        if(clientService.getClientById(clientService.getClientByEmail(client.getEmail()).getIdUser()) != null) {
            System.out.println("id a modifier: "+clientService.getClientById(clientService.getClientByEmail(client.getEmail()).getIdUser()).getIdUser());
            if(client.getPassword().equals(client.getConfirmPassword())) {
                System.out.println("mot de passe a modifier: "+client.getPassword());
                Client u = clientService.getClientById(clientService.getClientByEmail(client.getEmail()).getIdUser());
                u.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
                u.setConfirmPassword(bCryptPasswordEncoder.encode(client.getConfirmPassword()));
                clientService.addClient(u);
                ra.addFlashAttribute("success", "votre mot de passe a bien été modifié");
                return "redirect:/login";
            }else {
                model.addAttribute("error", "la confirmation du mot de passe est incorrecte !");
                model.addAttribute("client", clientService.getClientByEmail(client.getEmail()));
                return "client/formPassword";
            }
        }else {
            ra.addFlashAttribute("notFound", "l'utilisateur avec cette adresse mail n'a pas de compte");
            return "redirect:/compte/password/recuver";
        }
    }

}
