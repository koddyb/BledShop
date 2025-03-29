package com.soft.MarketPlace.repository;

import com.soft.MarketPlace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("update User u set u.isValide = ?1 where u.idUser = ?2")
    boolean ActiveUser(String isValide, int idUser);
    User findByEmail(String email);
}
