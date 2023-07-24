package com.ezbytes.SpringbootSecuritydemo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.PackagePrivate;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Course> courses;

    private String email;
    private long total;
}
