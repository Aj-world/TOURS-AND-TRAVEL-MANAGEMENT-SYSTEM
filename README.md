# Tours and Travel Management System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Authentication-blue)
![Database](https://img.shields.io/badge/Database-H2%20%7C%20MySQL-blue)
![Build Tool](https://img.shields.io/badge/Build-Maven-red)
![Payment](https://img.shields.io/badge/Payment-Razorpay-purple)
![Architecture](https://img.shields.io/badge/Architecture-Server--Side--Rendering-yellow)

![GitHub Repo stars](https://img.shields.io/github/stars/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub forks](https://img.shields.io/github/forks/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub issues](https://img.shields.io/github/issues/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub license](https://img.shields.io/github/license/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)

---

# Project Description

The **Tours and Travel Management System** is a full-stack travel booking web application developed using **Spring Boot, Spring Security, Razorpay Payment Gateway, JPA/Hibernate, and Thymeleaf**.

The platform allows users to explore travel packages, create bookings, and securely complete payments.  
Administrators can manage travel packages, users, bookings, and payment records through a dedicated admin interface.

This project demonstrates important backend development concepts such as:

- MVC architecture
- Secure authentication with Spring Security
- Payment gateway integration
- Database management using JPA/Hibernate
- Server-side rendering using Thymeleaf

---

# Project Status

This repository contains a **legacy academic project that has been stabilized and updated**.

Recent updates include:

- Fixing Spring Security configuration issues
- Resolving runtime startup problems
- Updating dependencies for **Spring Boot 3**
- Organizing project architecture (DTO, Service, Repository)
- Improving payment processing flow
- Adding exception handling

---

# Tech Stack

## Backend

- Java 21
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Hibernate ORM

## Frontend

- Thymeleaf
- HTML5
- CSS3
- JavaScript

## Database

- H2 Database (Development)
- MySQL Compatible Configuration

## Payment Integration

- Razorpay Payment Gateway

## Build Tool

- Maven

## Development Tools

- IntelliJ IDEA / Eclipse
- Lombok
- Spring Boot DevTools
- Git & GitHub

---

# Project Overview

The application simplifies travel package booking and management through a secure web platform.

Users can:

- Register and login
- Browse travel packages
- View detailed package information
- Book travel packages
- Complete secure online payments
- Track booking status

Administrators can:

- Manage travel packages
- Manage users
- Monitor bookings
- Track payments
- Control system access using role-based authorization

---

# Server-Side Rendering Architecture

The application follows **Server-Side Rendering (SSR)** using **Spring Boot and Thymeleaf**.

Instead of rendering pages using frontend frameworks, the HTML pages are generated on the server.

### Rendering Flow

```
Client Request (Browser)
        ↓
Spring Boot Controller
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (JPA)
        ↓
Database
        ↓
Thymeleaf Template Engine
        ↓
Rendered HTML returned to browser
```

### Advantages

- Faster initial page load
- SEO-friendly pages
- Secure server-side processing
- Simplified frontend logic

---

# Key Features

## User Features

- User Registration and Login
- Secure authentication using Spring Security
- Browse travel packages
- View package details
- Online booking system
- Razorpay payment integration
- Booking confirmation

---

## Admin Features

- Admin dashboard
- Travel package management
- Booking management
- User management
- Payment monitoring
- Role-based access control

---

# Payment Gateway Integration

The system integrates **Razorpay Payment Gateway** for secure payment processing.

### Payment Workflow

```
User selects travel package
        ↓
Application creates Razorpay order
        ↓
User completes payment
        ↓
Payment verified by backend
        ↓
Booking confirmed and stored in database
```

---

# System Architecture

The application follows **MVC (Model-View-Controller)** architecture.

```
Client (Browser)
        │
        ▼
Controller Layer
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
Database
```

---

# Project Structure

```
src
 └── main
     ├── java
     │   └── com
     │       └── Aj
     │           ├── Controller
     │           ├── Service
     │           ├── Repository
     │           ├── Entity
     │           ├── DTO
     │           ├── SecurityConfiguration
     │           └── Exception
     │
     └── resources
         ├── static
         │   ├── css
         │   ├── js
         │   └── images
         │
         ├── templates
         │   ├── Admin
         │   ├── User
         │
         └── application.properties
```

---

# Frontend Preview (HTML UI)

A static preview of the application's **HTML interface** is available on Netlify.

Netlify Preview:

```
https://your-netlify-link.netlify.app
```

Note:

This preview only displays the **frontend UI**.

Full functionality such as authentication, booking logic, payment processing, and database operations requires the **Spring Boot backend server running locally**.

---

# Installation and Setup

## Prerequisites

- Java 21+
- Maven
- MySQL Server (optional)
- IDE (IntelliJ / Eclipse)

---

## Clone the Repository

```
git clone https://github.com/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM.git
```

---

## Navigate to Project

```
cd TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM
```

---

## Run the Application

Using Maven:

```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8081
```

---

## H2 Database Console

```
http://localhost:8081/h2-console
```

Database URL

```
jdbc:h2:mem:testdb
```

Username

```
sa
```

Password

```
(empty)
```

---

# Academic Certification

This project was developed as part of the **Master of Computer Applications (MCA)** academic curriculum.

It is a **certified college project**, and supporting academic documents are available including:

- Academic project report
- College certification
- Project approval documentation

---

# Future Improvements

Possible future enhancements:

- REST API support
- Docker containerization
- Pagination and sorting
- Email notifications for booking confirmation
- API documentation using Swagger
- Cloud deployment

---

# Author

**Abinash Nayak**

Java Backend Developer  
MCA Graduate

GitHub  
https://github.com/Aj-world

---

# License

This project is created for **educational and learning purposes**.