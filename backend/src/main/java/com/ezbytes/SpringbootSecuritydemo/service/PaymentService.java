package com.ezbytes.SpringbootSecuritydemo.service;

import com.ezbytes.SpringbootSecuritydemo.entity.Cart;
import com.ezbytes.SpringbootSecuritydemo.entity.Course;
import com.ezbytes.SpringbootSecuritydemo.entity.Payment;
import com.ezbytes.SpringbootSecuritydemo.repository.CartRepo;
import com.ezbytes.SpringbootSecuritydemo.repository.PaymentRepo;
import com.ezbytes.SpringbootSecuritydemo.utils.ExtractJwt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private EmailService emailService;


    @Value("${stripe.webhook.secretKey}")
    private String stripeWebhookSecret;

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ExtractJwt extractJwt;

    @Value("${stripe.secretKey}")
    private String stripeKey;


    public void webhookService(String payload, String signature) {
        try {

            // Verify the Stripe webhook signature
            Event event = Webhook.constructEvent(payload, signature, stripeWebhookSecret);

            // Handle the Stripe event based on its type
            String eventType = event.getType();
            switch (eventType) {
                case "checkout.session.completed":

                    System.out.println("------Checkout.session.completed-------");
                    JsonNode jsonNode = mapper.readTree(payload);
                    // Extract customer ID from the JSON
                    String customerId = jsonNode.get("data").get("object").get("customer").asText();
                    String paymentStatus = jsonNode.get("data").get("object").get("status").asText();
                    System.out.println("Payment Status: " + paymentStatus);
                    if ("complete".equals(paymentStatus)) {
                        //Retrieving the customer detail----->

                        Customer customer = Customer.retrieve(customerId);
                        Payment payment = mapper.readValue(customer.getMetadata().get("checkoutData"), Payment.class);
                        Set<String> courseNames = payment.getCourses().stream().map(course -> {
                            return course.getCourseName();
                        }).collect(Collectors.toSet());

                        //fetching the files
                        List<File> premiumAttachments = courseNames.stream().map(courseName -> {
                            return new File(getClass().getClassLoader().getResource(courseName + ".pdf").getFile());
                        }).collect(Collectors.toList());


                        emailService.sendEmail(customer.getEmail(), "Payment Successful", "Thank you for your payment. Your order has been successfully processed.Please look for the attachments sent", premiumAttachments);


                        System.out.println("Payment Done : " + paymentRepo.save(payment));


                        //clearing the cart

                        Cart cart = cartRepo.findByEmail(customer.getEmail()).get();
                        cartRepo.delete(cart);
                        System.out.println("Cart Emptied");

                    }

                    break;
                // Add more cases for other event types if needed
                default:
                    System.out.println("Default Event Type : " + eventType);
                    break;
            }


        } catch (SignatureVerificationException e) {
            // The signature verification failed, respond with a 400 Bad Request
            System.out.println(e.getStackTrace());
        } catch (Exception e) {
            // Handle other exceptions if needed
            System.out.println(e.getStackTrace());
        }
    }


    public String createStripeSession(String token) throws JsonProcessingException, StripeException {
        String email = extractJwt.payloadJWTExtraction(token);
        Stripe.apiKey = stripeKey;

        //getting cart data
        Cart cart = cartRepo.findByEmail(email).get();

        String checkoutData = mapper.writeValueAsString(cart);


        CustomerCreateParams.Builder customerBuilder = new CustomerCreateParams.Builder();
        customerBuilder.setEmail(email);

        //adding metda data
        Map<String, String> metadata = new HashMap<>();
        metadata.put("checkoutData", checkoutData);
        customerBuilder.setMetadata(metadata);

        Customer customer = Customer.create(customerBuilder.build());


        //creating the session

        SessionCreateParams.Builder sessionBuilder = new SessionCreateParams.Builder();
        sessionBuilder.setCustomer(customer.getId());
        //List of line  AKA the products which are in the cart
        sessionBuilder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);

        sessionBuilder.setSuccessUrl("http://localhost:5173/success");
        sessionBuilder.setCancelUrl("http://localhost:5173/error");


        List<SessionCreateParams.LineItem> lineItems = createLineItemsFromCart(cart);

        for (SessionCreateParams.LineItem lineItem : lineItems) {
            sessionBuilder.addLineItem(lineItem);
        }


        sessionBuilder.setMode(SessionCreateParams.Mode.PAYMENT);

        Session session = Session.create(sessionBuilder.build());
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("url", session.getUrl());

        return mapper.writeValueAsString(responseMap);
    }

    public static List<SessionCreateParams.LineItem> createLineItemsFromCart(Cart cart) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (Course course : cart.getCourses()) {
            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("inr") // Replace with your desired currency
                                    .setUnitAmount((long) (course.getUnitPrice() * 100)) // Amount in cents
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(course.getCourseName())
                                                    .setDescription(course.getCourseDescription())

                                                    .addImage(course.getImg()) // Add the image URL to metadata
                                                    .build()
                                    )
                                    .build()
                    )
                    .setQuantity(1L) // Assuming 1 quantity per course
                    .build();

            lineItems.add(lineItem);
        }

        return lineItems;
    }
}
