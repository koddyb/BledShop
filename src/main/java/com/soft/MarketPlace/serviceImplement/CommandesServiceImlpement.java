package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.*;
import com.soft.MarketPlace.repository.*;
import com.soft.MarketPlace.service.CommandeService;
import com.soft.MarketPlace.service.PanierService;
import com.soft.MarketPlace.service.ProduitService;
import com.soft.MarketPlace.service.UtilisateurService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.random.RandomGenerator;

@Service
public class CommandesServiceImlpement implements CommandeService {

    @Autowired
    private CommandesRepository commandesRepository;

    @Autowired
    private BoncommandeRepository boncommandeRepository;

    @Autowired
    private LignecommandesRepository lignecommandesRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    PanierService panierService;

    @Autowired
    ProduitsRepository produitsRepository;

    @Autowired
    private LivraisonRepository livraisonRepository;
//
//    List<Commande> CommandeListForNotification = new ArrayList<>();
//    List<Produit> ProduitListForNotification = new ArrayList<>();
    //le thread qui va se charger d'envoyer les notification
    Thread notification = null;

    private String sendCommandeMessage(Commande commande){
        String message = "<h2>Commande Recu</h2><br>" +
                "Salut à vous Monsieur/Madame " +
                commande.getUtilisateur().getNom()+
                "<br> vous venez de recevoir une commande de " +
                commande.getClient().getNom()+
                " email du client : " +
                commande.getClient().getEmail()+
                " téléphones du client : " +
                commande.getClient().getTelephone1()+
                "/" +
                commande.getClient().getTelephone2()+
                "<br> à livrer avant la date: " +
                commande.getDateButoire()+
                " pour un Montant Total de " +
                commande.getPrix()+
                "connectez vous à votre plateforme pour avoir les details de commande";

        return message;
    }

    private String sendSeuilMessage(Produit produit){
        String message = "<h2>Seuil Atteint</h2><br>" +
                "Salut à vous Monsieur/Madame " +
                produit.getUtilisateur().getNom()+
                "<br> votre article a atteint le seuil que vous avez précisé " +
                "<br> article/produit : " +
                produit.getLibelle()+
                " reference produit : " +
                produit.getReference()+
                "<br> seuil précisé : " +
                produit.getSeuil()+
                "<br> veillez vous connectez afin de modifier la quantité disponible" +
                "<br>Merci pour votre confiance";

        return message;
    }

    private ApiService apiService = new ApiService();

    public CommandesServiceImlpement(CommandesRepository commandesRepository) {
        this.commandesRepository = commandesRepository;
    }

    @Override
    public List<Commande> getAllCommande() {
        return commandesRepository.findAll();
    }

    @Override
    public Commande getCommandeById(int id) {
        return commandesRepository.findById(id).get();
    }

    @Override
    public Commande getCommandeByDateCommande(String date) {
        return commandesRepository.findByDateCommande(date);
    }

    @Override
    public List<Commande> getCommandeByEtat(Client client,String etat) {

        List<Commande> commandeClient = new ArrayList<>();
        List<Commande> commandes = commandesRepository.findByEtat(etat);
        for(Commande c: commandes){
            if(c.getClient() == client){
                commandeClient.add(c);
            }
        }
        return commandeClient;
    }

    @Override
    public List<Commande> getCommandeByEtats(String etat) {

        List<Commande> commandes = commandesRepository.findByEtat(etat);
        if(commandes == null){
            return null;
        }else {
            return commandes;
        }
    }

    @Override
    public List<Commande> getCommandeUtilisateurByEtat(Utilisateur utilisateur, String etat) {

        List<Commande> commandeUtilisateur = new ArrayList<>();
        List<Commande> commandes = commandesRepository.findByEtat(etat);
        for(Commande c: commandes){
            if(c.getUtilisateur() == utilisateur){
                commandeUtilisateur.add(c);
            }
        }
        return commandeUtilisateur;
    }

/*    @Override
    public Commande addCommande(Panier panier, String lieux, String numeroPaiement) {

        //calcul des dates pour finir dans 10 jours
        Date dateDuJour = new Date();
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(dateDuJour);
        calendrier.add(Calendar.DATE, 10);
        Date dateDansCinqJours = calendrier.getTime();


        List<LignePaniers> lignePaniers = new ArrayList<>();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        List<Produit> produits = new ArrayList<>();
        lignePaniers = panier.getLignePaniers();

        for(LignePaniers lp: lignePaniers){
            produits.add(lp.getProduit());
        }
        for (Produit p: produits){
            if(!utilisateurs.contains(p.getUtilisateur())){
               utilisateurs.add(p.getUtilisateur());
            }
        }

        Commande commandeUtilisateur = null;
        BonCommande bonCommande = null;

        System.out.println("nombre de user: "+utilisateurs.size());

        for(Utilisateur u: utilisateurs){
            //code pour le bon de commande

            String code = codeCreateur(10);

            Livraison livraison = new Livraison();

            commandeUtilisateur = new Commande();
            commandeUtilisateur.setDateCommande(dateDuJour);
            commandeUtilisateur.setEtat("en attente");
            commandeUtilisateur.setClient(panier.getClient());
            commandeUtilisateur.setDateButoire(dateDansCinqJours);
            commandeUtilisateur.setMoyenPaiement(numeroPaiement);
            commandeUtilisateur.setUtilisateur(u);

            List<LigneCommandes> ligneCommandeUtilisateur = new ArrayList<>();

            for (LignePaniers lp: lignePaniers){
                LigneCommandes ligneCommande = new LigneCommandes();
                if(u.getProduits().contains(lp.getProduit())){
                    ligneCommande.setProduit(lp.getProduit());
                    ligneCommande.setQuantite(lp.getQuantite());
                    ligneCommande.setCouleur(lp.getCouleur());
                    ligneCommande.setTaille(lp.getTaille());
                    ligneCommande.setPrix(lp.getQuantite()*lp.getProduit().getPrix());
                    ligneCommande.setCommande(commandeUtilisateur);
                }
                ligneCommandeUtilisateur.add(ligneCommande);
            }
            commandeUtilisateur.setLigneCommandes(ligneCommandeUtilisateur);
            float prixTotal = totalPrix(ligneCommandeUtilisateur);
            commandeUtilisateur.setPrix(prixTotal);
            //notification de l'utilisateur
            commandesRepository.save(commandeUtilisateur);

            for(LigneCommandes lc: commandeUtilisateur.getLigneCommandes()){
                Produit p = produitsRepository.findById(lc.getProduit().getIdProduit()).get();
                p.setQuantite(p.getQuantite() - lc.getQuantite());
                produitsRepository.save(p);
            }

            bonCommande = new BonCommande();

            bonCommande.setCommande(commandeUtilisateur);

            bonCommande.setClient(panier.getClient());

            bonCommande.setCode(code);

            boncommandeRepository.save(bonCommande);

            livraison.setCommande(commandeUtilisateur);

            livraison.setDateLivraison(dateDansCinqJours);

            livraison.setLieu(lieux);

            livraison.setEtat(false);

            livraisonRepository.save(livraison);

            for(LigneCommandes lc: ligneCommandeUtilisateur){
                lignecommandesRepository.save(lc);
            }
        }

        for(Produit p : produits){
            panierService.supprimerLignePanier(p, panier.getClient());
        }

        return panier.getClient().getCommandes().get(0);
    }*/

/*    private float totalPrix(List<LigneCommandes> ligneCommandes){
        float totalPrix = 0;

        if(ligneCommandes == null){
            return totalPrix;
        }

        for(LigneCommandes lc: ligneCommandes){
            totalPrix += lc.getPrix();
        }
        return totalPrix;
    }*/

    @Override
    public Commande addCommande(Panier panier, String lieux, String numeroPaiement) {

        //calcul des dates pour finir dans 10 jours
        Date dateDuJour = new Date();
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTime(dateDuJour);
        calendrier.add(Calendar.DATE, 10);
        Date dateDansCinqJours = calendrier.getTime();


        List<LignePaniers> lignePaniers = panier.getLignePaniers();
        List<Utilisateur> utilisateurs = new ArrayList<>();
        List<Commande> commandes = new ArrayList<>();
        List<LigneCommandes> ligneCommandes = new ArrayList<>();
        List<BonCommande> bonsCommande = new ArrayList<>();
        List<Livraison> livraisons = new ArrayList<>();
        List<Produit> produits = new ArrayList<>();

        for(LignePaniers lp: lignePaniers){
            Produit produit = lp.getProduit();
            Utilisateur utilisateur = produit.getUtilisateur();
            produits.add(lp.getProduit());
            if(!utilisateurs.contains(utilisateur)){
                utilisateurs.add(utilisateur);
            }
        }

        for(Utilisateur u: utilisateurs){
            //code pour le bon de commande
            String code = codeCreateur(10);

            Livraison livraison = new Livraison();

            Commande commandeUtilisateur = new Commande();
            commandeUtilisateur.setDateCommande(dateDuJour);
            commandeUtilisateur.setEtat("en attente");
            commandeUtilisateur.setClient(panier.getClient());
            commandeUtilisateur.setDateButoire(dateDansCinqJours);
            commandeUtilisateur.setMoyenPaiement(numeroPaiement);
            commandeUtilisateur.setUtilisateur(u);

            List<LigneCommandes> ligneCommandeUtilisateur = new ArrayList<>();

            for (LignePaniers lp: lignePaniers){
                LigneCommandes ligneCommande = new LigneCommandes();
                if(u.getProduits().contains(lp.getProduit())){
                    ligneCommande.setProduit(lp.getProduit());
                    ligneCommande.setQuantite(lp.getQuantite());
                    ligneCommande.setCouleur(lp.getCouleur());
                    ligneCommande.setTaille(lp.getTaille());
                    ligneCommande.setPrix(lp.getQuantite()*lp.getProduit().getPrix());
                    ligneCommande.setCommande(commandeUtilisateur);
                    ligneCommandeUtilisateur.add(ligneCommande);
                }
            }

            commandeUtilisateur.setLigneCommandes(ligneCommandeUtilisateur);
            float prixTotal = totalPrix(ligneCommandeUtilisateur);
            commandeUtilisateur.setPrix(prixTotal);

            //notification de l'utilisateur
            commandes.add(commandeUtilisateur);

            for(LigneCommandes lc: commandeUtilisateur.getLigneCommandes()){
                Produit p = produitsRepository.findById(lc.getProduit().getIdProduit()).get();
                p.setQuantite(p.getQuantite() - lc.getQuantite());
                produitsRepository.save(p);

                ligneCommandes.add(lc);
            }

            BonCommande bonCommande = new BonCommande();
            bonCommande.setCommande(commandeUtilisateur);
            bonCommande.setClient(panier.getClient());
            bonCommande.setCode(code);

            bonsCommande.add(bonCommande);

            livraison.setCommande(commandeUtilisateur);
            livraison.setDateLivraison(dateDansCinqJours);
            livraison.setLieu(lieux);
            livraison.setEtat(false);

            livraisons.add(livraison);
        }

        // Ajouter les éléments à la base de données
        commandesRepository.saveAll(commandes);
        lignecommandesRepository.saveAll(ligneCommandes);
        boncommandeRepository.saveAll(bonsCommande);
        livraisonRepository.saveAll(livraisons);

        for(Produit p : produits){
            panierService.supprimerLignePanier(p, panier.getClient());
        }

        notification = new Thread(new notifieur(commandes,produits));
        notification.start();

        return panier.getClient().getCommandes().get(0);
    }

    private float totalPrix(List<LigneCommandes> ligneCommandes){
        float totalPrix = 0;

        if(ligneCommandes == null){
            return totalPrix;
        }

        for(LigneCommandes lc: ligneCommandes){
            totalPrix += lc.getPrix();
        }
        return totalPrix;
    }

    @Override
    public Commande updateCommande(Commande commande) {
        return commandesRepository.save(commande);
    }

    @Override
    public void deleteCommande(int id) {
        commandesRepository.deleteById(id);
    }

    @Override
    public List<Commande> getCommandeByUtilisateur(Utilisateur utilisateur) {
        return commandesRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public List<Commande> getCommandeByClient(Client client) {
        return commandesRepository.findByClient(client);
    }

    //valider la commande de lutilisateur si le code du bon est correct
    @Override
    public Commande validerCommande(Commande commande, String code, Utilisateur utilisateur, Client client) {
//on effectue toute les verifications
        BonCommande bonCommande = new BonCommande();
        bonCommande = boncommandeRepository.findByCode(code);

        if(bonCommande == null || commande==null || utilisateur == null || client == null){
            return null;
        }

        BonCommande bonCommande1 = commande.getBonCommande();

        if(bonCommande1 == null){
            return null;
        }

        if(commande.getUtilisateur().getNom() != utilisateur.getNom()){
            return null;
        }

        Commande commandeToActive = commandesRepository.findById(commande.getIdCommande()).get();
        if(commandeToActive == null){
            return null;
        }

        Livraison livraison = livraisonRepository.findById(commandeToActive.getLivraison().getIdLivraison()).get();
        if(livraison == null){
            return null;
        }

//on passe le paiement a vrai avant de modifier la commande
        if(bonCommande1 == bonCommande && bonCommande1.getClient() == client){
           commandeToActive.setEtat("livrer");
           //ajout du chiffre d'affaires
           Utilisateur utilisateur1 = utilisateurService.getUtilisateurById(utilisateur.getIdUser());
           utilisateur1.setChiffreAffaire(utilisateur1.getChiffreAffaire()+commande.getPrix());
           livraison.setEtat(true);
           //paiement

           //fin du paiement
           livraisonRepository.save(livraison);
           return commandesRepository.save(commandeToActive);
        }else{
            return null;
        }
    }

    public String codeCreateur(int longueur){
        String theAlphaNumericS;
        StringBuilder builder;

        theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "123456789";

        //create the StringBuffer
        builder = new StringBuilder(longueur);

        for (int m = 0; m < longueur; m++) {

            // generate numeric
            int myindex
                    = (int)(theAlphaNumericS.length()
                    * Math.random());

            // add the characters
            builder.append(theAlphaNumericS
                    .charAt(myindex));
        }

        return builder.toString();
    }

    //trying
    class notifieur implements Runnable{

        List<Commande> commandesS = new ArrayList<>();
        List<Produit> produitsS = new ArrayList<>();

        notifieur(List<Commande> commandesS, List<Produit> produitsS){
            this.commandesS = commandesS;
            this.produitsS = produitsS;
        }
//cette classe va envoyer les notifications en parallele
        @Override
        public void run() {
            //envoie du mail a tous les utilisateur
            for (Commande c: commandesS){
                try {
                    apiService.sendmail(c.getUtilisateur().getEmail(),sendCommandeMessage(c),"Nouvelle Commande");
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }

            //verifications de stock
            for(Produit p: produitsS){
                if(p.getQuantite() <= p.getSeuil()){
                    try {
                        apiService.sendmail(p.getUtilisateur().getEmail(),sendSeuilMessage(p),"Seuil atteint");
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
