package com.ezbytes.SpringbootSecuritydemo.controller;

import com.ezbytes.SpringbootSecuritydemo.payload.CourseDto;
import com.ezbytes.SpringbootSecuritydemo.service.CourseService;
import com.ezbytes.SpringbootSecuritydemo.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {

  private final CourseService courseService;
  private final PaymentService paymentService;

  @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(){

        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);


    }


    @GetMapping("/user/{id}")
    public ResponseEntity check(@RequestHeader("Authorization") String token,@PathVariable("id") Long id) throws JsonProcessingException, StripeException {

              Boolean checkIfBought =   courseService.checkIfCourseBought(token,id);
              if(checkIfBought){
                  System.out.println("bought");
                  return ResponseEntity.badRequest().build();
              }
        System.out.println("not bought");
              return ResponseEntity.ok().build();

    }
}
