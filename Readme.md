## Table of Contents

1. [Overview](#Overview)
2. [Setup to run](#Setup-Run-App)
3.[User Structure](#User-Structure)
4.[DailyUsage-Structure](#DailyUsage-Structure)
5.[Cycle-Structure)](#Cycle-Structure)
6.[Improvements](#Future-Improvements)
       
# Overview
This assignment involves creating a production-level Spring Boot Java REST server for a micro-service architecture environment. The server will use MongoDB (version 5.0 or higher) as its database. The system will manage customer data, billing cycles, and daily usage for a telecommunications service.
Key components:

Database Schema:

Cycle collection (10 million documents)
User collection (1 million documents)
Daily_Usage collection (50 million documents)

REST APIs:

Get current cycle daily usage for a customer
Get cycle history for a given phone number (MDN)
Create a new user
Update existing user profile


Technical Considerations:

Use of Spring Boot for creating the REST server
MongoDB integration
JSON for input and output formats
JWT-based authentication (assumed to be handled by a higher-level micro-service)
Handling of frequent updates (every 15 minutes) for usage data
Support for transferring phone numbers between users

# Setup Run App

## Prerequisites

- Docker
- Docker Compose 
- Java 
- Maven

## Setup

1. Install Docker on your machine if you haven't already.
2. Install java
3. Add Maven dependencies
4. Start a MongoDB container:
   docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=rootuser -e MONGO_INITDB_ROOT_PASSWORD=rootpass mongo

## Configuration

The application is configured to connect to MongoDB with the following details:

- Host: localhost
- Port: 27017
- Database: admin
- Username: rootuser
- Password: rootpass

These settings can be found in the `application.properties` file.

## Running the Application

1. Build the application using your preferred method (e.g., Maven).

2. Run the application.

3. The application should now be running and connected to the MongoDB instance.

## API Documentation
Swagger UI is configured for this application. You can access it at:
http://localhost:8080/swagger-ui-custom.html


## User Structure

# User Class
### User Class Fields
- `id`: String (MongoDB document ID)
- `firstName`: String
- `lastName`: String
- `email`: String
- `password`: String (write-only for JSON serialization)
- `mdn`: String (Mobile Directory Number)
- `createdDate`: LocalDateTime (automatically set on creation)
- `lastModifiedDate`: LocalDateTime (automatically updated on modification)

# User Service
The `UserService` class is a Spring service that handles user-related operations such as:

1. `getAllUsers()`: Retrieves all users from the database.
2. `createUser(User user)`: Creates a new user, checking for existing phone numbers.
3. `UpdateUser(User user, String userid)`: Updates an existing user's information.
4. `transferMdn(String userid, String mdn)`: Transfers a phone number to a different user.
5. `getUser(String userId, String mdn)`: Retrieves a specific user by ID and validates the MDN.

## Key Components

### UserService

The main service class that implements the business logic for user operations.

### UserRepository

An interface that likely extends MongoDB's repository interface for database operations.

### User

A model class representing the user entity.

### Custom Exception


A custom exception class for handling user-related errors.

### Validation

Input validation is performed using a custom `Validator` class. This ensures that user input is properly sanitized and meets the required format before processing.


## Notes

- Password encryption is commented out but appears to be a planned feature.
- The service uses MongoDB for data persistence.
- Lombok's `@AllArgsConstructor` is used to generate a constructor with all fields.

### API Endpoints

1. **Get All Users**
- Endpoint: `GET /api/users`
- Description: Retrieves all users in the system.

2. **Create User**
- Endpoint: `POST /api/users`
- Description: Creates a new user.
- Body: User object in JSON format

3. **Update User**
- Endpoint: `PUT /api/users/{userId}`
- Description: Updates an existing user's information.
- Path Variable: `userId`
- Body: Updated User object in JSON format

4. **Transfer MDN**
- Endpoint: `PUT /api/users/{userId}/mdn`
- Description: Transfers an MDN to a specified user.
- Path Variable: `userId`
- Body: New MDN in JSON format


## Error Handling
The service throws CustomException for various error conditions, such as:
* Duplicate phone numbers
* User not found
* Invalid MDN


# DailyUsage-Structure

## DailyUsage Model Structure

### Fields
- `id`: String (MongoDB document ID)
- `mdn`: String (Mobile Directory Number)
- `userId`: String (Reference to the User)
- `usageDate`: LocalDateTime (Date of the usage record)
- `usedInMb`: double (Amount of data used in megabytes)
- `createdDate`: LocalDateTime (automatically set on creation)
- `lastModifiedDate`: LocalDateTime (automatically updated on modification)

## Daily Usage Service

This service manages daily data usage for users in a mobile application.

## Features

- Create daily usage records
- Update usage for existing records
- Retrieve usage data between specified dates

### DailyUsageService

Main service class handling daily usage operations.

### DailyUsageRepository

Interface for database operations (not shown in the provided code).

### DailyUsage

Model class representing daily usage data.

## Key Methods

1. `createDailyUsage(String userId, String mdn)`
   - Creates a new daily usage record
   - Prevents duplicate entries for the same day

2. `updateUsage(String id, double usedInMb)`
   - Updates the data usage for an existing record

3. `getUsageBetweenDates(String userId, String mdn, LocalDate startDate, LocalDate endDate)`
   - Retrieves usage data for a user within a specified date range

# Daily Usage API

This Spring Boot REST controller manages daily usage operations for the US Mobile Assignment project.

## Endpoints

### Create Daily Usage
- **URL:** `/create-daily-usage`
- **Method:** POST
- **Description:** Creates a new daily usage entry for a user.
- **Query Parameters:** 
  - userId (String)
  - mdn (String)
- **Response:** DailyUsage object

### Update Daily Usage
- **URL:** `/create-daily-usage/{id}`
- **Method:** PUT
- **Description:** Updates the usage for an existing daily usage entry.
- **Path Variable:** id (String)
- **Query Parameter:** usedInMb (double)
- **Response:** Updated DailyUsage object

## Error Handling

The controller uses a custom `CustomException` for error scenarios. Ensure proper error handling is implemented in the calling code.

## Validation

Input validation is performed using a custom `Validator` class. This ensures that user input is properly sanitized and meets the required format before processing.


# Cycle-Structure

## Cycle Model Structure

### Fields
- `id`: String (MongoDB document ID)
- `mdn`: String (Mobile Directory Number)
- `startDate`: LocalDate (Start date of the cycle)
- `endDate`: LocalDate (End date of the cycle)
- `userId`: String (Reference to the User)
- `createdDate`: LocalDateTime (automatically set on creation)
- `lastModifiedDate`: LocalDateTime (automatically updated on modification)

## Features

- Create a new cycle for a user
- End an existing cycle
- Retrieve daily usage for the current cycle
- Get cycle history for a user

## Main Components

### CycleService

The `CycleService` class provides functionality to create, end, and query usage cycles for mobile users. 
It interacts with other services and repositories to manage cycle data and daily usage information.

#### Methods:

1. `createCycle(String userId, String mdn)`: Creates a new cycle for a user.
2. `endCycle(String cycleId)`: Ends an existing cycle.
3. `getCurrentCycleDailyUsage(String userId, String mdn)`: Retrieves daily usage for the current cycle.
4. `getCycleHistoryByMdn(String userId, String mdn)`: Gets the cycle history for a user.

### Supporting Classes

- `Cycle`: Model class representing a usage cycle.
- `DailyUsage`: Model class representing daily usage data.
- `CycleRepository`: Repository for cycle data.
- `DailyUsageRepository`: Repository for daily usage data.
- `UserService`: Service for user-related operations.
- `DailyUsageService`: Service for daily usage operations.

The service uses custom CustomException for error scenarios such as:

- Cycle already exists
- Cycle not found
- User not found

## Endpoints

### 1. Get Current Cycle Daily Usage

- **URL:** `/current-daily-usage`
- **Method:** GET
- **Parameters:**
  - `userId` (String): User ID
  - `mdn` (String): Mobile Device Number
- **Response:** List of `DailyUsageResponse` objects
- **Status Code:** 200 OK

### 2. Get Cycle History

- **URL:** `/cycle-history`
- **Method:** GET
- **Parameters:**
  - `userId` (String): User ID
  - `mdn` (String): Mobile Device Number
- **Response:** List of `CycleHistoryResponse` objects
- **Status Code:** 200 OK

### 3. Create Cycle

- **URL:** `/cycle`
- **Method:** POST
- **Parameters:**
  - `userId` (String): User ID
  - `mdn` (String): Mobile Device Number
- **Response:** `Cycle` object
- **Status Code:** 200 OK

## Exception Handling

The controller throws a `CustomException` for invalid input or other user-related errors. These exceptions should be handled appropriately in a global exception handler.

## Input Validation

Input validation is performed using the `Validator` class:

- `validateId(userId)`: Validates the user ID
- `validatePhoneNumber(mdn)`: Validates the mobile device number

# Future Improvements

- Add new fields as needed (e.g., cycle type, status)
- Add new fields as needed (e.g., voice minutes, Free SMS count International)
- Add pagination for a better user experience
- Implement password encryption (currently commented out)
- Implement user authentication and authorization, role-based access control
- Implement caching to improve performance for frequently accessed data


