package com.ezbytes.SpringbootSecuritydemo.repository;

import com.ezbytes.SpringbootSecuritydemo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {


    Optional<Payment> findByEmail(String email);
}
