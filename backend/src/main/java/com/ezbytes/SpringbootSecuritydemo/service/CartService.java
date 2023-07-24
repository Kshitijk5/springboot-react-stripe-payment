package com.ezbytes.SpringbootSecuritydemo.service;

import com.ezbytes.SpringbootSecuritydemo.entity.Cart;
import com.ezbytes.SpringbootSecuritydemo.entity.Course;
import com.ezbytes.SpringbootSecuritydemo.repository.CartRepo;
import com.ezbytes.SpringbootSecuritydemo.repository.CourseRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final CartRepo cartRepository;
    private final CourseRepo courseRepository;


    public Cart addCourse(String email, Long courseId) throws Exception {
        System.out.println("Email : "+email);
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isEmpty()) {
            throw new Exception("No course with id-" + courseId + " found");
        }

        Optional<Cart> cart = cartRepository.findByEmail(email);
        List<Course> tempCourses = List.of(course.get());

        if (cart.isEmpty()) {
            Cart newCart = new Cart();
            newCart.setCourses(tempCourses);
            newCart.setEmail(email);
            newCart.setTotal(Long.valueOf(course.get().getUnitPrice()));
            return cartRepository.save(newCart);
        } else {
            List<Course> userCourse = cart.get().getCourses();
            if (userCourse.stream().noneMatch(ccourse -> ccourse.getId().equals(courseId))) {
                userCourse.add(course.get());
                cart.get().setCourses(userCourse);
                cart.get().setTotal(Long.valueOf(cart.get().getTotal() + course.get().getUnitPrice()));
                return cartRepository.save(cart.get());
            } else
                throw new Exception("Course is already present in the user's cart");

        }


    }


    public Cart getCart(String email) {

        Optional<Cart> cart = cartRepository.findByEmail(email);
        if(cart.isPresent())
            return cart.get();

        return null;
    }

    public Cart deleteCartItem(String email, Long id) throws Exception {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new Exception("No course with id-" + id + " found");
        }

        Optional<Cart> cart = cartRepository.findByEmail(email);
        if (cart.isPresent()) {
            Cart userCart = cart.get();
            List<Course> cartCourses = userCart.getCourses();
            cartCourses.removeIf(c -> c.getId().equals(id));
            userCart.setCourses(cartCourses);

            // Recalculate the total price after removing the course
            long total = cartCourses.stream().mapToLong(Course::getUnitPrice).sum();
            userCart.setTotal(total);

            return cartRepository.save(userCart);
        } else {
            throw new Exception("Cart not found for email: " + email);
        }
    }
}
