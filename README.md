# Leave Management System - Backend 

This repository contains the backend for a modern Leave Management System, built with Spring Boot. It provides a robust RESTful API for handling users, leave requests, and team management.

The corresponding React frontend for this project can be found here:
**[leave-management-frontend](https://github.com/Sanjal28/leave-management-frontend)**

---

## ✨ Key Features

The system supports two distinct user roles with specific functionalities:

### 👨‍💼 Manager
-   Register and log in to the system.
-   View all employees on their team.
-   View, **approve**, or **reject** leave requests from their team.
-   Update the leave balance for any team member.
-   View team analytics (counts of pending, approved, and rejected leaves).

### 👷 Employee
-   Register and log in to the system.
-   View their current leave balance.
-   Submit a new leave request to their manager.
-   View their complete leave history with statuses.

---

## 🛠️ Tech Stack

-   **Java 17+**
-   **Spring Boot:** For the core application framework.
-   **Spring Security (JWT):** For secure, token-based authentication and authorization.
-   **Spring Data JPA & Hibernate:** For database interaction.
-   **MySQL:** As the relational database.
-   **Maven:** For project and dependency management.

---

## 🚀 Getting Started

Follow these steps to get the backend running locally.

### Prerequisites

-   JDK 17 or newer
-   Apache Maven
-   MySQL Server

### Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Sanjal28/leave-management-system.git](https://github.com/Sanjal28/leave-management-system.git)
    cd leave-management-system
    ```

2.  **Setup the Database:**
    -   Open your MySQL client.
    -   Create a new database named `leave_system_db`.

3.  **Configure Environment Variables:**
    -   Create a file named `.env` in the root of the project.
    -   Copy the contents from `.env.example` and fill in your database credentials and a secure JWT secret.

    ```env
    # .env file content
    DB_CONNECTION_URL=jdbc:mysql://localhost:3306/leave_system_db?createDatabaseIfNotExist=true
    DB_USERNAME=your_mysql_username
    DB_PASSWORD=your_mysql_password
    JWT_SECRET=your-super-secret-key-that-is-long-and-secure-enough
    ```

4.  **Build and Run the Application:**
    -   Let Maven install the dependencies.
    -   Run the application using your IDE or the Maven command:
        ```bash
        mvn spring-boot:run
        ```

5.  **Server is Live!**
    -   The backend will start on `http://localhost:8080`.
    -   The API is now ready to accept requests from the frontend.

---

## 📋 API Endpoints

A brief overview of the main API routes:

| Method | Endpoint                    | Description                                | Access      |
| :----- | :-------------------------- | :----------------------------------------- | :---------- |
| `POST` | `/api/auth/signup`          | Register a new manager or employee.        | Public      |
| `POST` | `/api/auth/login`           | Authenticate a user and get a JWT.         | Public      |
| `GET`  | `/api/employee/**`          | Endpoints for employee-specific actions.   | Employee    |
| `GET`  | `/api/manager/**`           | Endpoints for manager-specific actions.    | Manager     |
