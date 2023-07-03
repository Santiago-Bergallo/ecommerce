package com.example.ecommerceApp.model.dao;

import com.example.ecommerceApp.model.LocalUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface LocalUserDao extends CrudRepository<LocalUser, Long> {
    Optional<LocalUser> findByUsernameIgnoreCase(String username);

    Optional<LocalUser> findByEmailIgnoreCase(String email);





}
