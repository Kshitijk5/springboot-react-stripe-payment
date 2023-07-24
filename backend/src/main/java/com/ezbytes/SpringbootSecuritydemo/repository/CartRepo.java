package com.ezbytes.SpringbootSecuritydemo.repository;

import com.ezbytes.SpringbootSecuritydemo.entity.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {

    Optional<Cart> findByEmail(String email);
}
