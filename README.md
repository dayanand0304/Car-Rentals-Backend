# 🚗 Car Rentals Platform
**Enterprise-grade backend system for managing car rental operations with real-world business logic, security, and scalable architecture.**

<p align="center">
  <b>Built with Java • Spring Boot • JWT • Docker • PostgreSQL</b>
</p>

<p align="center">
  <a href="https://car-rentals-z9i2.onrender.com">🌐 Live Demo</a> •
  <a href="https://car-rentals-z9i2.onrender.com/swagger-ui/index.html">📄 API Docs</a>
</p>

---

## 🧠 Product Vision

This project is designed as a real-world car rental backend system, inspired by platforms like Zoomcar and Hertz.

It focuses on:
- Accurate billing logic
- Data integrity
- Secure authentication
- Maintainable backend architecture

---

## ✨ Key Highlights

- 💰 **Advanced Pricing Engine**  
  Calculates total rental cost including taxes, late fees, and damage penalties

- 🔄 **Soft Delete Strategy (Production Pattern)**  
  Uses `ACTIVE / INACTIVE` states instead of deletion to preserve historical data

- 🔧 **Damage & Repair Lifecycle**  
  Cars move through states: `AVAILABLE → RENTED → DAMAGED → REPAIRED → AVAILABLE`

- 📊 **Operational Insights**  
  Track overdue rentals and damaged rentals separately

- 🔐 **JWT-Based Security**  
  Stateless authentication with secure API access

- 📄 **Interactive API Documentation**  
  Swagger UI for testing and exploration

---

## 🏗️ System Architecture

### 📌 High-Level Flow
    Client (Frontend / Postman / Swagger)
                    │
                    ▼
            REST Controller Layer
                    │
                    ▼
            Service Layer (Business Logic)
                    │
                    ▼
        Repository Layer (JPA / Hibernate)
                    │
                    ▼
            Database (MySQL / PostgreSQL)

---

### 🧩 Internal Components

          Client
              │
              ▼
        REST Controller Layer
              │
              ▼
        Data Transfer Objects(DTO) <-> Mapper Layer
              │
              ▼
        Service Layer
              │
              ▼
        Repository Layer
              │
              ▼
          Database


---

### ⚙️ Cross-Cutting Concerns

- 🔐 JWT Authentication Filter
- ⚠️ Global Exception Handling
- 🔄 Transaction Management

---

## 🛠️ Tech Stack

### Backend
- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)

### Database
- MySQL (Development)
- PostgreSQL (Production - Render)

### Dev Tools
- Swagger (OpenAPI)
- Docker
- Maven

---

## ⚙️ Getting Started

### 🔧 Prerequisites
- Java 17+
- Maven
- MySQL

---

## 📥 Clone the Repository

```bash
git clone https://github.com/dayanand0304/Car_Rentals.git
cd Car_Rentals
```

---

## ⚙️ Configure Database

~~~properties
spring.datasource.url= jdbc:mysql://localhost:3306/car_rentals
spring.datasource.username= your_username
spring.datasource.password= your_password
~~~
---

## ▶️ Run Locally

```bash
  mvn clean install
  mvn spring-boot:run
```

---

## 🐳 Run with Docker

```bash
  docker build -t car-rentals .
  docker run -p 8080:8080 car-rentals
```

---

## 🔐 Authentication Flow

~~~markdown
User Login → Generate JWT → Send Token → Validate Token → Access Secured APIs
~~~

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## 📚 API Documentation

<p align="left">
  <a href="https://car-rentals-z9i2.onrender.com/swagger-ui/index.html">📄 Swagger Link</a>
</p>

- Test endpoints
- View schemas
- Understand API flows

---

## 🗄️ Database Design

### Core Entities

- User
- Car
- Rental

### Design Decisions

- Soft delete via status field
- Relational mapping using JPA
- Clean and normalized schema

---


## 📦 Deployment

<p align="left">
  <a href="https://car-rentals-z9i2.onrender.com"> 🌐 Live Application Link</a>
</p>

- Deployed and accessible via public URL with fully functional APIs.

### 🚀 Deployment Stack

- Render (Cloud Hosting)
- PostgreSQL (Production DB)
- Environment-based configuration

---

## 🧪 Testing

### Current

- Manual testing using Swagger & Postman

### Planned
- Unit Testing (JUnit, Mockito)
- Integration Testing (Spring Boot Test)
---

## 📸 Screenshots


---

## Sample API

### Login

- POST /api/auth/login

### Request

~~~json
{
  "email": "example@gmail.com",
  "password": "Password@123"
}
~~~

### Response

~~~json
{
  "token": "your_jwt_token"
}
~~~

---


## 🚀 Future Enhancements

- 🧪 Add Unit Testing (JUnit, Mockito) for service layer validation
- 🔗 Implement Integration Testing using Spring Boot Test
- ⚙️ Set up CI/CD pipeline with GitHub Actions for automated builds & deployment
- 📦 Improve Docker setup with multi-stage builds
- 📊 Add monitoring & logging (Spring Actuator, Prometheus)  


---


## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Dayanand**  
Backend Developer | Java & Spring Boot

- GitHub: https://github.com/dayanand0304

---

