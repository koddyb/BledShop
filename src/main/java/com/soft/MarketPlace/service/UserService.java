package com.soft.MarketPlace.service;

import com.soft.MarketPlace.entity.User;
import com.soft.MarketPlace.entity.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public boolean activeUser(User user);
}
