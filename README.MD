# GUIDE

```bash
    mvn clean install
    mvn spring-boot:run
```

## API Design for Vintech

### Database Schema

#### 0. Table: Admin

- `id`: Integer (Primary Key)
- `username`: String (Unique)
- `password`: String
- `email`: String (Unique)
- `app_key`: String (Unique)

#### 1. Table: Employee

- `id`: Integer (Primary Key)
- `username`: String (Unique)
- `position`: String
- `name`: String
- `email`: String (Unique)

#### 2. Table: Attendance

- `id`: Integer (Primary Key)
- `employee_id`: Integer (Foreign Key -> Employee)
- `date`: Date
- `check_in`: Time
- `check_out`: Time
- `total_hours`: Decimal

#### 3. Table: Salary

- `id`: Integer (Primary Key)
- `employee_id`: Integer (Foreign Key -> Employee)
- `hourly_salary`: Decimal
- `total_salary`: Decimal

#### 4. Table: Notification

- `id`: Integer (Primary Key)
- `employee_id`: Integer (Foreign Key -> Employee)
- `message`: Text
- `created_at`: Timestamp
- `updated_at`: Timestamp

---

## API Endpoints

### Admin API

#### Authentication

- `POST /api/admin/sign-up`: Admin registration
- `POST /api/admin/login`: Admin login
- `POST /api/admin/logout`: Admin logout

#### Information Admin

- `GET /api/admin/information`: Get admin information
- `PATCH /api/admin/change-password`: Update admin password
- `PATCH /api/admin/change-information`: Update admin information

#### Employee Management

- `POST /api/admin/employees`: Create a new employee
- `PATCH /api/admin/employees/:id`: Update employee by ID
- `DELETE /api/admin/employees/:id`: Delete employee by ID
- `GET /api/admin/employees/:id`: Get employee details by ID
- `GET /api/admin/employees`: Get all employees

#### Attendance Management

- `GET /api/admin/attendance`: Get all attendance records
- `GET /api/admin/attendance/:id`: Get attendance by employee ID

#### Salary Management

- `GET /api/admin/salary/base`: Get base salary for all employees
- `PATCH /api/admin/salary/base`: Update base salary for all employees
- `GET /api/admin/salary/base/:id`: Get base salary for a specific employee
- `PATCH /api/admin/salary/base/:id`: Update base salary for a specific employee
- `GET /api/admin/salary/total/:id`: Get total salary for a specific employee
- `PATCH /api/admin/salary/total/:id`: Update total salary for a specific employee

#### Notification Management

- `GET /api/admin/notifications`: Get all notifications
- `GET /api/admin/notifications/email`: Get email notification
- `POST /api/admin/notifications/:id`: Send notification to an employee
- `POST /api/admin/notifications/email`: Send email notification to employees

---

### Employee API

#### Authentication Employee

- `POST /api/employees/login`: Employee login
- `POST /api/employees/logout`: Employee logout
- `POST /api/employees/sign-up`: Employee registration

#### Profile Management

- `PATCH /api/employees/profile`: Update employee profile

#### Attendance

- `POST /api/employees/attendance/check-in`: Employee check-in
- `POST /api/employees/attendance/check-out`: Employee check-out
- `GET /api/employees/attendance`: Get attendance for the logged-in employee

#### Salary

- `GET /api/employees/salary`: Get salary details for the logged-in employee

#### Notifications

- `GET /api/employees/notifications`: Get notifications for the logged-in employee

---

### Middleware

#### Authentication Middleware

- `SetCookie`: Middleware for setting and verifying authentication cookies

#### Authorization Middleware

- `VerifyCookie`: Middleware for verifying authentication cookies

---

## Services

### Admin Services

- `updateTotalSalary()`: Update total salary for employees
- `sendNotification()`: Send notifications to employees
- `sendAllNotification()`: Send all notifications to employees
