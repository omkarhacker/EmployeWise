# EmployWise Assignment with MongoDB

## Setup Instructions

### Prerequisites
1. Java 17 or higher
2. Maven
3. MongoDB (running locally or accessible)

### MongoDB Setup
1. Install MongoDB (https://www.mongodb.com/try/download/community)
2. Make sure MongoDB service is running (default port 27017)
3. No need to create database manually - it will be created automatically on first use

### Running the Application
1. Clone the repository
2. Run `mvn clean install`
3. Run `mvn spring-boot:run`
4. The application will start on port 8080


# EmployWise Employee Management API

## Base URL
`http://localhost:8080/api/employees`

## API Endpoints

### 1. Add a New Employee
Creates a new employee record in the database.

- **Endpoint**: `POST /`
- **Request Body**:
  ```json
  {
    "employeeName": "John Doe",
    "phoneNumber": "1234567890",
    "email": "john.doe@example.com",
    "reportsTo": "manager-id-here",  // optional field
    "profileImage": "https://example.com/profile.jpg"
  }
  ```
- **Response (Success - 201 Created)**:
  ```json
  "5f9d1b2b3c4d5e6f7a8b9c0d"  // Generated employee ID
  ```
- **Error Responses**:
  - `400 Bad Request`: Invalid input data
  - `500 Internal Server Error`: Server error

---

### 2. Get All Employees
Retrieves all employees with pagination and sorting support.

- **Endpoint**: `GET /`
- **Query Parameters**:
  - `page`: Page number (default: 0)
  - `size`: Items per page (default: 10)
  - `sortBy`: Field to sort by (default: `employeeName`, options: `employeeName`, `email`, `phoneNumber`)

- **Response (Success - 200 OK)**:
  ```json
  {
    "content": [
      {
        "id": "5f9d1b2b3c4d5e6f7a8b9c0d",
        "employeeName": "John Doe",
        "phoneNumber": "1234567890",
        "email": "john.doe@example.com",
        "reportsTo": "manager-id-here",
        "profileImage": "https://example.com/profile.jpg"
      }
    ],
    "pageable": {
      "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
      },
      "pageNumber": 0,
      "pageSize": 10,
      "offset": 0,
      "paged": true,
      "unpaged": false
    },
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "numberOfElements": 1,
    "first": true,
    "empty": false
  }
  ```

---

### 3. Delete an Employee
Deletes an employee by their ID.

- **Endpoint**: `DELETE /{id}`
- **Path Parameter**:
  - `id`: Employee ID to delete

- **Response (Success - 204 No Content)**

- **Error Responses**:
  - `404 Not Found`: Employee not found

---

### 4. Update an Employee
Updates an existing employee's information.

- **Endpoint**: `PUT /{id}`
- **Path Parameter**:
  - `id`: Employee ID to update

- **Request Body**:
  ```json
  {
    "employeeName": "John Doe Updated",
    "phoneNumber": "9876543210",
    "email": "john.updated@example.com",
    "reportsTo": "new-manager-id",  // optional
    "profileImage": "https://example.com/new-profile.jpg"
  }
  ```

- **Response (Success - 200 OK)**:
  ```json
  {
    "id": "5f9d1b2b3c4d5e6f7a8b9c0d",
    "employeeName": "John Doe Updated",
    "phoneNumber": "9876543210",
    "email": "john.updated@example.com",
    "reportsTo": "new-manager-id",
    "profileImage": "https://example.com/new-profile.jpg"
  }
  ```

---

### 5. Get Nth Level Manager
Retrieves the nth level manager for a given employee.

- **Endpoint**: `GET /{id}/manager/{level}`
- **Path Parameters**:
  - `id`: Employee ID to find manager for
  - `level`: Manager level (1 = direct manager, 2 = manager's manager, etc.)

- **Response (Success - 200 OK)**:
  ```json
  {
    "id": "manager-id-here",
    "employeeName": "Manager Name",
    "phoneNumber": "1122334455",
    "email": "manager@example.com",
    "reportsTo": "higher-manager-id",
    "profileImage": "https://example.com/manager.jpg"
  }
  ```

---

## Validation Rules
- `employeeName`: Required, non-blank
- `phoneNumber`: Required, exactly 10 digits
- `email`: Required, valid email format
- `profileImage`: Required, non-blank URL
- `reportsTo`: Optional, must reference valid employee ID if provided
```