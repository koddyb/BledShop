package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.Client;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;

public interface ClientService {

    //toutes les methodes qui concernent le client

    public List<Client> getAllClient();
    public Client getClientById(int id);
    public List<Client> getClientByNom(String nom);
    public List<Client> getClientByPrenom(String prenom);
    public Client getClientByEmail(String email);
    public Client addClient(Client client);
    public Client updateClient(Client client);
    public void deleteClient(int id);
}
