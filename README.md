# springboot-react-stripe-payment
# SoftwareNotesHub

SoftwareNotesHub is a full-stack web application that enables users to purchase courses and pay for them using the Stripe payment gateway. The project showcases the integration of Auth0 for user authentication and authorization, and Stripe for secure payment processing. This repository contains both the backend built with Spring Boot and the frontend built with React and Vite.

## Demo
- ## Main page(Not logged in state)
![Main page](https://i.imgur.com/IYuwvoW.png)
- ## Login
![Main page](https://i.imgur.com/lihl579.png)
- ## Main page(Logged in state)
![Main page](https://i.imgur.com/StGrvkD.png)
- ## Main page (Added to cart)
![Main page](https://i.imgur.com/yvCo8SO.png)
- ## Cart page
![Main page](https://i.imgur.com/ylq34n9.png)
- ## Stripe checkout page
![Main page](https://i.imgur.com/p6mjQX8.png)
- ## Stripe test payment page
![Main page](https://i.imgur.com/nYhBIXK.png)
- ## Success page
![Main page](https://i.imgur.com/eNQzo1n.png)
- ## Customer email
![Main page](https://i.imgur.com/w1pEsuA.png)


## Features

- **User Authentication:** Utilizing Auth0, users can register and log in securely, and their accounts are protected with JSON Web Tokens (JWTs).

- **Course Catalog:** A comprehensive list of available courses is displayed, including course names, descriptions, and prices.

- **Shopping Cart:** Users can add desired courses to their shopping cart, where they can review the cart summary before proceeding to payment.

- **Secure Payment:** The Stripe payment gateway ensures a safe and smooth payment experience for users.

- **Payment Success Page:** After successful payment, users are redirected to a success page confirming their purchase.

- **Email Notifications:** Upon successful payment, users receive an email containing the paid course materials.

## Tech Stack

### Backend

- **Spring Boot:** A robust Java framework for building powerful and scalable applications.

- **Spring Data JPA:** Simplifies data access and manipulation with the database using Java Persistence API.

- **Spring Starter Web:** Enables building web applications with Spring and simplifies the setup.

- **JavaMail Sender:** Provides email sending capabilities for your application.

- **MySQL Driver:** Provides connectivity to a MySQL database.

- **Lombok:** Reduces boilerplate code and simplifies the Java classes.

- **ModelMapper:** Facilitates object mapping between entities and DTOs.

- **Auth0:** An authentication and authorization service used to secure user accounts and resources.

- **Stripe SDK:** Enables secure and seamless payment processing with the Stripe payment gateway.

### Frontend

- **React:** A popular JavaScript library for building user interfaces.

- **Vite:** A fast build tool for modern web development.

- **Auth0 SDK:** Used to implement user authentication in the frontend.

- **Bootstrap:** CSS Framework for developing responsive and mobile-first websites..

## Installation

1. **Clone the Repository:**

```bash
 https://github.com/Kshitijk5/springboot-react-stripe-payment.git

 cd springboot-react-stripe-payment
```


## Contributing
 - Contributions are welcome! If you find any issues or want to add new features, feel free to submit a pull request.
