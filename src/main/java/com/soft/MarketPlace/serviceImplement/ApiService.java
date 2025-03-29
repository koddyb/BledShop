package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Client;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Component
public class ApiService {
    public ApiService() {}

    BCryptPasswordEncoder crypto = new BCryptPasswordEncoder();

    public void sendmail(String addresse, String message, String titre) throws AddressException, MessagingException {
        //définition des propriétés

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        //initiation de la session
        //ici nous allons utilisé l'authentification par mot de passe de Gmail

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("africayamoo@gmail.com", "nkfntlxwumllmylj");
            }
        });

        //ici on definit la provenance du mail

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("AfricaYamoo", false));

        // on renseigne le recepteur du message

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(addresse));

        msg.setSubject(titre);

        msg.setContent("ce message contient du contenu provenant de l'application", "text/html");

        msg.setSentDate(new Date());

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        //MimeBodyPart attachPart = new MimeBodyPart();

        //attachPart.attachFile("/var/tmp/image19.png");
        //multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg); }

    public String BuyCommand(Client client, String numeroPaiement, int montant,String adresse){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("service_secret", "mj00KmzQ61honxzaV4cyTf7hBJvtRkA16meQiBzjKgQiWqCIkBcTDMqOlYqXlSdb");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set("Content-Type","application/x-www-form-urlencoded");
        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        //creation de la requette
        requestMap.add("amount",1);
        requestMap.add("phone","656437088");
        requestMap.add("return_url","http://localhost:9090/commande/confirmer/"+crypto.encode(""+(client.getCommandes().get(client.getCommandes().toArray().length - 1)).getIdCommande()).replace("/","slash")+"/"+adresse.replace(" ","kkk"));
        System.out.println("1");
        // Effectuez un appel POST à l'API de Monetbil pour facturer une transaction mobile money
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestMap, headers);
        System.out.println("2 "+requestEntity);
        ResponseEntity <Map> response = restTemplate.exchange("https://api.monetbil.com/widget/v2.1/hyGSax5VukoXi3S3byk5keiBl6k3QMzD", HttpMethod.POST, requestEntity,Map.class);
        System.out.println("resultat: "+response);
        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("result: "+response.getBody());
            Map<String, Object> stringObjectMap = new HashMap<>();
            stringObjectMap = response.getBody();
            boolean status = (boolean) stringObjectMap.get("success");
            if (status) {
                RedirectView redirectView = new RedirectView();
                String paymentUrl = (String) stringObjectMap.get("payment_url");
                System.out.println("url: "+paymentUrl);
                // Effectuez une redirection vers la page de paiement
                return paymentUrl;
            // Gérer la réponse
        } else {
            return null;
        }

        }

        return null;
    }
}


