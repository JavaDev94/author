# 📌 Author Management API

This is a REST API for author and author works, built with **Spring Boot (Java 21)** and **PostgreSQL**.

---

## 🚀 Features
✔️ **Author**: Get from DB if it doesn't exist take from https://openlibrary.org/dev/docs/api/authors and save it in DB.  
✔️ **Works**: Get list  of author works from DB it doesn't exist take from https://openlibrary.org/authors/OL23919A/works.json and save it in DB.   
✔️ **Database**: Uses MySQL with Liquibase migrations.  
✔️ **Containerized**: Run with Docker and Docker Compose.

---

## 📦 Requirements
Before running the project, ensure you have installed:
- [JDK 21](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Docker & Docker Compose](https://www.docker.com/products/docker-desktop)
- [MySQL](https://dev.mysql.com/) (if not using Docker)

---

## 🔧 Project Setup


## Build the Project
Make sure you have Maven installed, then run:
``` sh
mvn clean package -DskipTests
```
This will generate a JAR file in the target/ directory.

🐳 Running with Docker
## Run with Docker Compose
The easiest way to start the app is using Docker Compose:

``` sh 
    docker-compose up -d
 ```   
    

This will:

Start the MySQL database.
Start the Spring Boot application.
To stop the services, run:
```sh
   docker-compose down
```

## 🛠 Running Without Docker
If you want to run the project without Docker:

1️⃣ Start MySQL
Ensure a MySQL instance is running and update your application.properties
``` sh
    spring.datasource.url=jdbc:mysql://localhost:3307/author_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    spring.datasource.username=author_user
    spring.datasource.password=author_pass
    spring.jpa.show-sql=true
    spring.liquibase.change-log=classpath:/db.changelog/db.changelog-master.yml
    spring.liquibase.enabled=true
```
2️⃣ Run the Spring Boot Application
```sh
    mvn spring-boot:run
```
## 🔥 API Endpoints
Method  Endpoint  Description

 GET  | /api/v1/authors/search?name=J%20K%20Rowling | Get author details 

GET  | /api/v1/authors/1/works | Get author works details


📌 Base URL: http://localhost:8080