# 🚗 Car Rental Management System – Spring Boot Backend

## 🧩 Project Summary
A production-style **Car Rental Management System** built using **Spring Boot** following clean architecture and RESTful API design principles.

The system enables:
- Users to browse cars and book rentals
- Admins to manage cars, customers, and reservations

It demonstrates real-world backend development practices such as:
- Layered architecture
- DTO pattern
- Validation
- Logging
- Global exception handling

---

## 🧠 Pattern
- REST API Development
- Layered Architecture
- DTO & Mapper Pattern
- Service-based Business Logic
- Repository-based Data Access

---

## 💡 Key Insight
Instead of mixing responsibilities, the project strictly separates layers:

- Controller handles requests
- Service handles business logic
- Repository handles database operations
- DTO secures data transfer
- Mapper converts Entity ↔ DTO

This guarantees:
- Clean architecture
- Scalability
- Maintainability
- Interview-ready backend design

---

## 🛠️ Approach
1. Designed entities for **User, Car, Booking**
2. Implemented layered architecture structure
3. Created DTOs for request/response handling
4. Added mapper classes for conversions
5. Implemented service layer for business logic
6. Built REST controllers for API endpoints
7. Added validation and global exception handling
8. Integrated logging
9. Connected MySQL database using JPA

---

## ⏱️ Complexity
- Optimized DB operations using JPA
- Scalable service structure
- Loose coupling between layers
- Easy to extend for security & microservices

---

## ❗ Common Challenges Solved
- Soft delete implementation
- Validation at controller level
- Logging for debugging & monitoring

---

## 💼 Layers
- Controller → Handles HTTP requests
- Service → Business logic
- Repository → Database interaction
- DTO → Data transfer between layers
- Mapper → Converts Entity ↔ DTO
- Exception Handler → Centralized error handling


---

## ⚙️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- REST APIs

### Database
- MySQL

### Tools
- Maven
- Git & GitHub
- Postman

---

## 🚀 Features Implemented

### 👤 User Module
- Register user
- View profile
- Soft delete/reactivate user

### 🚘 Car Module
- Add car
- Update car
- Delete car
- View available cars

### 📅 Booking Module
- Create booking
- View bookings
- Manage rentals

---

## 👨‍💻 Author
**Dayanand**

- Spring Boot
- REST APIs
- MySQL
- Backend Architecture 

