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

## About

The **Tours and Travel Management System** is a full-stack travel booking web application built with **Spring Boot, Spring Security, Razorpay, JPA/Hibernate, and Thymeleaf**.

Users can explore travel packages, make bookings, and complete secure payments. Administrators manage packages, users, bookings, and payments through a dedicated dashboard.

> **Frontend Preview (no backend required):** [tours-and-travel-management-system.netlify.app](https://tours-and-travel-management-system.netlify.app)

---

## Tech Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java 21, Spring Boot 3, Spring Security, Spring Data JPA, Hibernate |
| **Frontend** | Thymeleaf, HTML5, CSS3, JavaScript |
| **Database** | H2 (development), MySQL (production) |
| **Payment** | Razorpay Payment Gateway |
| **Build** | Maven |
| **Tooling** | Lombok, Spring Boot DevTools, IntelliJ IDEA / Eclipse |

---

## Features

### User
- Register and log in securely
- Browse and view travel packages
- Book packages and complete online payments via Razorpay
- Track booking status

### Admin
- Manage travel packages, users, and bookings
- Monitor payments
- Role-based access control

---

## Architecture

The application uses **MVC (Model-View-Controller)** with **Server-Side Rendering (SSR)** via Thymeleaf.

```
Client Request (Browser)
        ↓
Spring Boot Controller
        ↓
Service Layer (Business Logic)
        ↓
Repository Layer (Spring Data JPA)
        ↓
Database (H2 / MySQL)
        ↓
Thymeleaf renders HTML → returned to browser
```

---

## Payment Flow

```
User selects travel package
        ↓
App creates Razorpay order
        ↓
User completes payment
        ↓
Backend verifies payment
        ↓
Booking confirmed and saved to database
```

---

## Project Structure

```
src/main
├── java/com/Aj
│   ├── Controller
│   ├── Service
│   ├── Repository
│   ├── Entity
│   ├── DTO
│   ├── SecurityConfiguration
│   └── Exception
└── resources
    ├── static
    │   ├── css
    │   ├── js
    │   └── images
    ├── templates
    │   ├── Admin
    │   └── User
    └── application.properties
```

---

## Getting Started

### Prerequisites

- Java 21+
- Maven
- MySQL (optional — H2 is used by default)
- IntelliJ IDEA or Eclipse

### Clone and Run

```bash
git clone https://github.com/Aj-world/TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM.git
cd TOURS-AND-TRAVEL-MANAGEMENT-SYSTEM
mvn spring-boot:run
```

App runs at: **http://localhost:8081**

### H2 Console (Development)

| Setting | Value |
|---|---|
| URL | http://localhost:8081/h2-console |
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(leave blank)* |

---

## Screenshots

| Home Page | Travel Packages |
|---|---|
| ![Home Page](screenshots/home.png) | ![Packages](screenshots/packages.png) |

| Booking Page | Admin Dashboard |
|---|---|
| ![Booking](screenshots/booking.png) | ![Admin](screenshots/admin-dashboard.png) |

---

## Recent Updates

- Fixed Spring Security configuration issues
- Resolved runtime startup problems
- Updated dependencies for Spring Boot 3
- Improved project architecture (DTO, Service, Repository)
- Enhanced payment processing flow
- Added exception handling

---

## Roadmap

- [ ] REST API support
- [ ] Docker containerization
- [ ] Pagination and sorting
- [ ] Email notifications for booking confirmation
- [ ] Swagger API documentation
- [ ] Cloud deployment

---

## Academic Certification

This project was developed as part of the **Master of Computer Applications (MCA)** curriculum and is supported by academic documentation including a project report, college certification, and project approval.

---

## Author

**Abinash Nayak** — Java Backend Developer, MCA Graduate

GitHub: [github.com/Aj-world](https://github.com/Aj-world)

---

## License

This project is created for **educational and learning purposes**.
