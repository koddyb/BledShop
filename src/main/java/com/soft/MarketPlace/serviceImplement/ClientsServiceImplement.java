package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Client;
import com.soft.MarketPlace.entity.Role;
import com.soft.MarketPlace.repository.ClientsRepository;
import com.soft.MarketPlace.service.ClientService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientsServiceImplement implements ClientService {

    private ClientsRepository clientsRepository;

    public ClientsServiceImplement(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }


    @Override
    public List<Client> getAllClient() {
        return clientsRepository.findAll();
    }

    @Override
    public Client getClientById(int id) {
        return clientsRepository.findById(id).get();
    }

    @Override
    public List<Client> getClientByNom(String nom) {
        return clientsRepository.findByNom(nom);
    }

    @Override
    public List<Client> getClientByPrenom(String prenom) {
        return clientsRepository.findByPrenom(prenom);
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }

    @Override
    public Client addClient(Client client) {
        return clientsRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientsRepository.save(client);
    }

    @Override
    public void deleteClient(int id) {
        clientsRepository.deleteById(id);
    }

}
