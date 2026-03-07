# Tours and Travel Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Build Tool](https://img.shields.io/badge/Build-Maven-red)
![Payment](https://img.shields.io/badge/Payment-Razorpay-purple)
![Architecture](https://img.shields.io/badge/Architecture-SSR-yellow)

A full-stack Travel Booking Web Application built using Spring Boot, Spring Security, JWT Authentication, Razorpay Payment Gateway, MySQL, and Thymeleaf.

A full-stack Travel Booking Web Application built using Spring Boot, Spring Security, JWT Authentication, Razorpay Payment Gateway, MySQL, and Thymeleaf.

The system allows users to explore travel packages, securely book tours, and make online payments, while administrators can manage packages, bookings, and users through an admin dashboard.

This project demonstrates real-world backend development concepts including secure authentication, payment integration, database management, and MVC architecture.

---

Tech Stack

Backend

- Java 17
- Spring Boot 3
- Spring Security
- JWT Authentication (JJWT)
- Spring Data JPA
- Hibernate ORM

Frontend

- Thymeleaf
- HTML5
- CSS3
- JavaScript

Database

- MySQL

Payment Integration

- Razorpay Payment Gateway

Build & Dependency Management

- Maven

Development Tools

- Lombok
- Spring Boot DevTools

---

Project Overview

The Tours and Travel Management System simplifies travel package booking and management through a secure and structured platform.

The system enables users to:

- Register and login securely
- Browse available travel packages
- View package details
- Book travel packages
- Make secure online payments

Administrators can manage the system through a dashboard that allows them to control packages, bookings, and user information.

This project demonstrates the implementation of a secure and scalable web application using modern Java backend technologies.

---

Server-Side Rendering Architecture

This application follows a Server-Side Rendering (SSR) approach using Spring Boot and Thymeleaf.

Instead of rendering pages in the browser using JavaScript frameworks, the HTML views are generated on the server side and sent directly to the client.

Rendering Flow:

Client Request (Browser)
↓
Spring Boot Controller
↓
Service Layer (Business Logic)
↓
Database Access using JPA
↓
Thymeleaf Template Engine renders HTML
↓
Fully rendered page returned to browser

Benefits of this architecture:

- Faster initial page load
- SEO friendly pages
- Secure server-side data processing
- Simplified frontend logic

---

Key Features

User Features

- User Registration and Login
- Secure authentication using JWT tokens
- Browse travel packages
- View package details
- Book travel packages
- Secure online payments using Razorpay
- Booking confirmation system

---

Admin Features

- Admin Dashboard
- Travel package management
- Booking management
- User management
- Payment monitoring
- Role-based access control

---

Security Implementation

Security is implemented using Spring Security with JWT Authentication.

Authentication Flow:

User Login
↓
Credentials verified by Spring Security
↓
JWT Token generated
↓
Token sent to client
↓
Client sends token in Authorization header
↓
Server validates token for each request

Example Authorization Header:

Authorization: Bearer <JWT_TOKEN>

This ensures stateless and secure authentication.

---

Payment Gateway Integration

The system integrates Razorpay Payment Gateway for handling secure online payments.

Payment Workflow:

User selects travel package
↓
System creates Razorpay order
↓
User completes payment using Razorpay
↓
Payment verified by backend
↓
Booking confirmation stored in database

This demonstrates real-world third-party API integration within a Spring Boot application.

---

System Architecture

The application follows MVC (Model-View-Controller) architecture.

Client (Browser)
        │
        ▼
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Repository (DAO) Layer
        │
        ▼
MySQL Database

---

Project Structure

src
 └── main
     ├── java
     │   └── com
     │       └── example
     │           ├── controller
     │           ├── service
     │           ├── dao
     │           ├── entity
     │           ├── security
     │           ├── dto
     │           └── exception
     │
     └── resources
         ├── static
         │   ├── css
         │   ├── js
         │   └── images
         │
         ├── templates
         │   ├── Admin
         │   ├── Authentication
         │   ├── Packages
         │   └── User
         │
         └── application.properties

---

Frontend Preview (HTML UI)

A static preview of the application's HTML interface is available on Netlify for quick UI review.

Netlify Preview:

https://your-netlify-link.netlify.app

Note:

This preview only shows the HTML user interface.
Full application functionality such as authentication, booking logic, payment processing, and database interaction requires the Spring Boot backend server.

---

Installation and Setup

Prerequisites

- Java 17+
- Maven
- MySQL Server
- IDE (IntelliJ / Eclipse / VS Code)

---

Clone Repository

git clone https://github.com/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM.git

---

Navigate to Project

cd TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM

---

Configure Database

Update database configuration in "application.properties".

spring.datasource.url=jdbc:mysql://localhost:3306/travel_db
spring.datasource.username=your_username
spring.datasource.password=your_password

---

Run the Application

Using Maven:

mvn spring-boot:run

Application will start at:

http://localhost:8080

---

Academic Certification

This project was developed as part of the Master of Computer Applications (MCA) academic curriculum.

It is a certified college project, and supporting academic documents are available including:

- Project approval letter
- Academic project report
- College certification documents

These documents verify the authenticity and official academic submission of this project.

---

Future Improvements

- REST API support
- Docker containerization
- Pagination and sorting
- Email notifications for booking confirmation
- API documentation using Swagger

---

Learning Outcomes

Through this project the following concepts were implemented:

- Spring Boot web application development
- Secure JWT authentication
- Payment gateway integration
- Database management using JPA/Hibernate
- Server-side rendering with Thymeleaf
- Role-based authorization using Spring Security

---

Author

Abinash Nayak

Java Backend Developer
MCA Graduate

GitHub
https://github.com/Aj-world

---

License

This project was created for educational and learning purposes.
