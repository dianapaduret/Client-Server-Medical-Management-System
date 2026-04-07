# Client-Server Medical Management System

A Java-based client-server application for managing patients, doctors, appointments, and treatments, developed using Object-Oriented Programming principles and a layered architecture.

## Overview

This project was built from scratch to simulate a real-world medical management system. It supports the main workflows of a clinic environment, including authentication, patient and doctor management, appointment handling, and treatment tracking.

The goal of the project was to practice building a modular Java application with clear separation of responsibilities and a scalable structure.

## Main Features

- patient registration and management
- doctor registration and management
- user login and authentication
- appointment scheduling and tracking
- treatment and diagnostic management
- modular client-server structure
- layered architecture for maintainability

## Architecture

The application is organized into multiple layers, each with a clear responsibility:

- **Model layer** – defines the core domain entities
- **DAO layer** – handles data access and persistence logic
- **Service layer** – contains the business logic of the application
- **UI layer** – manages the interaction with the user
- **Network layer** – supports the client-server communication
- **Utility layer** – contains validation and helper methods

---

## Technologies Used

- Java
- Maven
- Object-Oriented Programming
- Client-Server Architecture
- DAO Pattern
- Layered Architecture

---

## How to Run

1. Clone the repository  
2. Open the project in IntelliJ IDEA or Visual Studio Code with Java extensions  
3. Install dependencies using Maven  
4. Start the server  
5. Start the client  
6. Use the console menus to interact with the application  

---

## What I Learned

Through this project, I practiced:

- designing a layered software architecture  
- applying OOP principles in a complete Java application  
- separating persistence, business logic, and user interaction  
- organizing a project for readability and maintainability  
- building an application with a client-server oriented structure


## Project Structure

```text
src/
└── main/
    └── java/
        ├── dao/
        ├── db/
        ├── model/
        ├── network/
        ├── service/
        ├── ui/
        ├── util/
        └── Main.java

