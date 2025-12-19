Oline-shop
mall-swarm is a comprehensive microservices e-commerce system built with a modern Java tech stack, designed to help developers quickly build and deploy scalable online store applications. It integrates essential platform level services like service registry, configuration management, monitoring, API gateway and more, while also providing a Vue-based admin dashboard for easy usage.

---
🚀 Project Overview
mall-swarm is a full Spring Cloud Alibaba microservices e-commerce platform using:
- Spring Cloud Alibaba & Spring Boot 3.2
- Sa-Token for authentication
- MyBatis for ORM
- Elasticsearch for search capabilities
- Docker & Kubernetes for containerization and deployment
- Integrated platform services: service registry, config center, monitoring center, API gateway and more. 
This project is particularly suitable for learning microservices architecture and implementing real-world e-commerce systems using modern open-source technologies. (GitHub)

---
🧩 Features
✔ Microservices architecture powered by Spring Cloud Alibaba
✔ Centralized service registry & configuration
✔ Monitoring dashboards and logs
✔ API gateway for unified endpoint management
✔ Vue-based admin backend for quick system setup
✔ Elasticsearch for product search
✔ Docker and Kubernetes deployment support

---
📁 Architecture
The backend consists of multiple modules representing core system components:
mall
├── mall-common           // Shared utilities and common logic  
├── mall-mbg              // MyBatis generator database layer  
├── mall-auth             // Authentication & authorization server  
├── mall-gateway          // API Gateway service   
├── mall-admin            // System backend service  
├── mall-search           // Elasticsearch powered search service  
├── mall-portal           // Mobile portal microservice  
└── config                // Centralized configuration storage  
Each module represents a microservice or supporting component of the system. 

---
📦 Technologies Used
Backend
This content is only supported in a Feishu Docs

---
🔧 Getting Started
Prerequisites
Before running the project, make sure your development environment includes:
- JDK 17+
- MySQL 5.7
- Redis 7.0
- Elasticsearch 7.17.3
- MongoDB 5.0
- RabbitMQ 3.x
- Docker & Kubernetes setup 

---
📌 Quick Start
1. Clone the repository:
git clone https://github.com/Wuxioawu/onlineshop/tree/main
2. Import the project into your IDE and build.
3. Prepare dependent services (MySQL, Redis, Elasticsearch, etc.).
4. Configure the environment and start backend microservices separately or via Docker/K8s.
Detailed environment setup guides can be found in the project documentation. 

---
⚖️ License
This project is licensed under Apache License 2.0.
