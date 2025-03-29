package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findByNom(String nom);
}