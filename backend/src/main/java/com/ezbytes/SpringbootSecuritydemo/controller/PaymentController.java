package com.ezbytes.SpringbootSecuritydemo.controller;

import com.ezbytes.SpringbootSecuritydemo.entity.Cart;
import com.ezbytes.SpringbootSecuritydemo.entity.Course;
import com.ezbytes.SpringbootSecuritydemo.entity.Payment;
import com.ezbytes.SpringbootSecuritydemo.repository.CartRepo;
import com.ezbytes.SpringbootSecuritydemo.repository.PaymentRepo;
import com.ezbytes.SpringbootSecuritydemo.service.EmailService;
import com.ezbytes.SpringbootSecuritydemo.service.PaymentService;
import com.ezbytes.SpringbootSecuritydemo.utils.ExtractJwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

;

@RequestMapping("/api/pay")
@RestController
@AllArgsConstructor
public class PaymentController {


    public ObjectMapper mapper;



    private PaymentService paymentService;

    @PostMapping("/webhook")
    public void handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String signature) {
     paymentService.webhookService(payload,signature);

    }


    @PostMapping
    public ResponseEntity<String> pay(@RequestHeader("Authorization") String token) throws JsonProcessingException, StripeException {
       return new ResponseEntity<>(paymentService.createStripeSession(token),HttpStatus.OK);
    }



}


