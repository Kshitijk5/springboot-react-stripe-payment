package com.ezbytes.SpringbootSecuritydemo.service;

import com.ezbytes.SpringbootSecuritydemo.entity.Course;
import com.ezbytes.SpringbootSecuritydemo.entity.Payment;
import com.ezbytes.SpringbootSecuritydemo.payload.CourseDto;
import com.ezbytes.SpringbootSecuritydemo.repository.CourseRepo;
import com.ezbytes.SpringbootSecuritydemo.repository.PaymentRepo;
import com.ezbytes.SpringbootSecuritydemo.utils.ExtractJwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private final CourseRepo courseRepository;
    private final ModelMapper modelMapper;

    private final PaymentRepo paymentRepo;
    private final ExtractJwt extractJwt;

    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();


        List<CourseDto> coursesDto = courses.stream().map(course ->
                modelMapper.map(course, CourseDto.class)
        ).collect(Collectors.toList());

        return coursesDto;

    }

    public Boolean checkIfCourseBought(String token,Long courseId) throws JsonProcessingException {
        String email = extractJwt.payloadJWTExtraction(token);

        Optional<Payment> payment = paymentRepo.findByEmail(email);

        if (payment.isPresent()) {
            List<Course> courses = payment.get().getCourses();

            for (Course course : courses) {
               if(course.getId()==courseId){
                   return true;
               }
            }
        }
        return false;
    }


}
