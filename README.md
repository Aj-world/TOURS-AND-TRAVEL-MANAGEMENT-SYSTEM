# Tours and Travel Management System

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Build Tool](https://img.shields.io/badge/Build-Maven-red)
![Payment](https://img.shields.io/badge/Payment-Razorpay-purple)
![Architecture](https://img.shields.io/badge/Architecture-Server--Side--Rendering-yellow)

![GitHub Repo stars](https://img.shields.io/github/stars/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub forks](https://img.shields.io/github/forks/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub issues](https://img.shields.io/github/issues/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)
![GitHub license](https://img.shields.io/github/license/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM)

---

# Project Description

The **Tours and Travel Management System** is a full-stack travel booking web application developed using **Spring Boot, Spring Security, JWT Authentication, Razorpay Payment Gateway, MySQL, and Thymeleaf**.

The platform allows users to explore travel packages, securely book tours, and complete online payments.
Administrators can manage packages, bookings, users, and payments through a dedicated admin dashboard.

This project demonstrates real-world backend development concepts such as secure authentication, payment gateway integration, database management, MVC architecture, and server-side rendering.

---

# Tech Stack

## Backend

* Java 17
* Spring Boot 3
* Spring Security
* JWT Authentication (JJWT)
* Spring Data JPA
* Hibernate ORM

## Frontend

* Thymeleaf
* HTML5
* CSS3
* JavaScript

## Database

* MySQL

## Payment Integration

* Razorpay Payment Gateway

## Build Tool

* Maven

## Development Tools

* Lombok
* Spring Boot DevTools

---

# Project Overview

The application simplifies the process of travel package booking and management through a secure and structured platform.

Users can:

* Register and login securely
* Browse available travel packages
* View detailed package information
* Book travel packages
* Make secure online payments

Administrators can:

* Manage travel packages
* Manage users
* Monitor bookings
* Track payments
* Control system access using role-based authorization

---

# Server-Side Rendering Architecture

This application follows a **Server-Side Rendering (SSR)** architecture using **Spring Boot and Thymeleaf**.

Instead of rendering pages in the browser using JavaScript frameworks, the HTML views are generated on the server and delivered directly to the client.

### Rendering Flow

```
Client Request (Browser)
        ↓
Spring Boot Controller
        ↓
Service Layer (Business Logic)
        ↓
Database Access using JPA
        ↓
Thymeleaf Template Engine
        ↓
Rendered HTML sent to browser
```

### Advantages

* Faster initial page load
* SEO-friendly pages
* Secure server-side processing
* Simplified frontend logic

---

# Key Features

## User Features

* User Registration and Login
* Secure JWT Authentication
* Browse Travel Packages
* View Package Details
* Online Booking System
* Razorpay Payment Integration
* Booking Confirmation

---

## Admin Features

* Admin Dashboard
* Travel Package Management
* Booking Management
* User Management
* Payment Monitoring
* Role-Based Access Control

---

# Security Implementation

Security is implemented using **Spring Security with JWT Authentication**.

### Authentication Flow

```
User Login
      ↓
Spring Security validates credentials
      ↓
JWT Token generated
      ↓
Token returned to client
      ↓
Client sends token in Authorization header
      ↓
Server validates token for each request
```

### Authorization Header Example

```
Authorization: Bearer <JWT_TOKEN>
```

This ensures **secure and stateless authentication**.

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
Booking confirmation stored in database
```

This demonstrates **third-party API integration in a Spring Boot application**.

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
Repository (DAO) Layer
        │
        ▼
MySQL Database
```

---

# Project Structure

```
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
```

---

# Frontend Preview (HTML UI)

A static preview of the application's **HTML interface** is available on Netlify.

Netlify Preview:

https://your-netlify-link.netlify.app

Note:
This preview only displays the **frontend UI**.
Full functionality such as authentication, booking logic, payment processing, and database operations requires the **Spring Boot backend server**.

---

# Application Screenshots

You can add UI screenshots for quick preview.

Example folder structure:

```
screenshots
 ├── home.png
 ├── packages.png
 ├── booking.png
 └── admin-dashboard.png
```

---

# Installation and Setup

## Prerequisites

* Java 17+
* Maven
* MySQL Server
* IDE (IntelliJ / Eclipse / VS Code)

---

## Clone the Repository

```
git clone https://github.com/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM.git
```

---

## Navigate to the Project

```
cd TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM
```

---

## Configure Database

Update the database configuration in **application.properties**.

```
spring.datasource.url=jdbc:mysql://localhost:3306/travel_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## Run the Application

Using Maven:

```
mvn spring-boot:run
```

Application will start at:

```
http://localhost:8080
```

---

# Academic Certification

This project was developed as part of the **Master of Computer Applications (MCA)** academic curriculum.

It is a **certified college project**, and supporting academic documents are available including:

* Project approval letter
* Academic project report
* College certification documents

These documents verify the **official academic submission of this project**.

---

# Future Improvements

* REST API support
* Docker containerization
* Pagination and sorting
* Email notifications for booking confirmation
* API documentation using Swagger

---

# Learning Outcomes

Through this project the following concepts were implemented:

* Spring Boot web application development
* Secure JWT authentication
* Payment gateway integration
* Database management using JPA/Hibernate
* Server-side rendering with Thymeleaf
* Role-based authorization using Spring Security

---

# Contributing

Contributions are welcome.

If you would like to improve this project:

1. Fork the repository
2. Create a new feature branch
3. Commit your changes
4. Submit a pull request

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
