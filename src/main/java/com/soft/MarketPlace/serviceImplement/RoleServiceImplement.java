package com.soft.MarketPlace.serviceImplement;

import com.soft.MarketPlace.entity.Role;
import com.soft.MarketPlace.repository.RoleRepository;
import com.soft.MarketPlace.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImplement implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByNom(String nom){
        return roleRepository.findByNom(nom);
    }
}
