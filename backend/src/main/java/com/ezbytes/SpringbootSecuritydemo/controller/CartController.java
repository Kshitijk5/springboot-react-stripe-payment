package com.ezbytes.SpringbootSecuritydemo.controller;

import com.ezbytes.SpringbootSecuritydemo.entity.Cart;
import com.ezbytes.SpringbootSecuritydemo.service.CartService;
import com.ezbytes.SpringbootSecuritydemo.utils.ExtractJwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
@RestController
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ExtractJwt extractJwt;




    @PostMapping("/course/{id}")
    public ResponseEntity<Cart> addCourse(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws Exception {
       return  new ResponseEntity<Cart>(cartService.addCourse(extractJwt.payloadJWTExtraction(token),id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart( @RequestHeader("Authorization") String token) throws Exception {
        return  new ResponseEntity<Cart>(cartService.getCart(extractJwt.payloadJWTExtraction(token)), HttpStatus.OK);
    }


    @PutMapping("/course/{id}")
    public ResponseEntity<Cart> deleteCartItem(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) throws Exception {
        return  new ResponseEntity<Cart>(cartService.deleteCartItem(extractJwt.payloadJWTExtraction(token),id), HttpStatus.OK);
    }
}
