package com.ezbytes.SpringbootSecuritydemo.repository;


import com.ezbytes.SpringbootSecuritydemo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course,Long> {
}
