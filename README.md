# FIRE HOTEL

FIRE HOTEL is a full-stack web application built with Spring Boot and React JS, designed to provide a secure and efficient hotel management system.

## Features

- **Secure Backend**: RESTful API with JWT authentication and Spring Security
- **Modular Architecture**: Business logic in dedicated service layer for enhanced scalability
- **React Frontend**: Utilizes hooks for seamless state management and data synchronization
- **Role-Based Access Control**: Multi-level permissions to protect sensitive information

## Tech Stack

- **Backend**: Spring Boot, Spring Security, JWT
- **Frontend**: React JS, React Hooks

## Getting Started

### Prerequisites

- Java 17+
- Node.js 18+
- npm

### Backend Setup

```bash
git clone https://github.com/AshFire1/Fire-Hotel.git
cd fire-hotel/backend
./mvnw spring-boot:run
```

The backend server will start on `http://localhost:8080`.

### Frontend Setup

```bash
cd ../frontend
npm install
npm start
```

The frontend will be available at `http://localhost:3000`.

## Project Structure

```
fire-hotel/
├── backend/
│   ├── src/
│   │   ├── main/java/com/firehotel/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── service/
│   │   └── resources/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── hooks/
│   │   └── App.js
│   └── package.json
└── README.md
```


