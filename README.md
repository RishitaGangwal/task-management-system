# Task Management System

## Tech Stack
- Frontend: React + Tailwind
- Backend: Spring Boot
- Database: MySQL
- Authentication: JWT
- Caching: Redis

---

## Project Structure
task-management-system/
 ├── client (React)
 ├── server (Spring Boot)

Both frontend and backend are inside the same GitHub repository.

---

## Features
- JWT Authentication (Login)
- Role-based Access (ADMIN / USER)
- Create, Update, Delete Tasks
- Pagination & Filtering
- Comments on Tasks
- Notifications System
- Redis caching for performance improvement

---

## Setup Instructions

### Backend
cd server
mvn spring-boot:run

Backend runs at:
http://localhost:8080

---

### Frontend
cd client
npm install
npm start

Frontend runs at:
http://localhost:3000

---

## Authentication Flow
1. Login using email & password
2. Backend returns JWT token
3. Token stored in frontend
4. Token sent in every API request

Authorization Header:
Authorization: Bearer <JWT_TOKEN>

---

## API Documentation

### Auth API

POST /api/v1/auth/login

Request:
{
  "email": "admin@gmail.com",
  "password": "987654"
}

Response:
{
  "token": "jwt-token"
}

---

### Task APIs

POST /api/v1/tasks → Create task  
GET /api/v1/tasks?page=0&size=5 → Get all tasks  
GET /api/v1/tasks/{userId} → Get user tasks  
PATCH /api/v1/tasks/{id} → Update task  
DELETE /api/v1/tasks/{id} → Delete task  

---

### Comments API

POST /api/v1/tasks/comments

Request:
{
  "taskId": 1,
  "userId": 1,
  "comment": "Good work"
}

---

### Notifications API

GET /api/notifications/{userId}

---

## Default Users (Database)

Admin:
email: admin@gmail.com
password: 987654
role: ADMIN

User:
email: user@gmail.com
password: 123456
role: USER

---

## Redis Caching
- Used for caching frequently accessed data (tasks, notifications)
- Improves performance by reducing database load

---

## Logging
- Spring Boot logging enabled for debugging
- Redis cache logs enabled for monitoring

---

## Scalability
- JWT is stateless → supports horizontal scaling
- Redis reduces DB load
- Can be extended into microservices:
  - Auth Service
  - Task Service
  - Notification Service

---

## Author
Full Stack Task Management System (Assignment Project)
