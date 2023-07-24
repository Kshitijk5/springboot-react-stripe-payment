package com.ezbytes.SpringbootSecuritydemo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String img;

    private String courseName;

    private String courseDescription;

    private Long unitPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
