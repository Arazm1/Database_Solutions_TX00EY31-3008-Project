# Webshop REST API



## 1. Overview
This project is a REST API backend for a small webshop, built using a provided relational database schema in MariaDB.

The application provides endpoints for managing webshop data, including products, customers, suppliers, and orders. It uses Spring Boot to expose REST endpoints and Spring Data JPA to communicate with the database.

The project also demonstrates database features such as indexing, views, triggers, transaction management, validation, error handling, and database security. These are described more thorougly later.


## Table of Contents

1. [Overview](#1-overview)
2. [Technology Stack](#2-technology-stack)
3. [Installation and Setup](#3-installation-and-setup)
4. [Configuration](#4-configuration)
5. [Running the Application](#5-running-the-application)
6. [System Architecture](#6-system-architecture)
7. [Project Structure](#7-project-structure)
8. [API Overview](#8-api-overview)
9. [Database Features](#9-database-features)
   - [Indexes and Query Optimization](#indexes-and-query-optimization)
   - [Database Views](#database-views)
   - [Triggers and Price History](#triggers-and-price-history)
   - [Transactions and Concurrency](#transactions-and-concurrency)
   - [Validation](#validation)
   - [Temporal Data](#temporal-data)
   - [Database Security](#database-security)
   - [Database Monitoring](#database-monitoring)
10. [Error Handling](#10-error-handling)
11. [API Documentation and Testing](#11-api-documentation-and-testing)
12. [Summary](#12-summary)




## 2. Technology Stack

Java — application programming language
Spring Boot — application framework
Spring Web — REST API endpoints
Spring Data JPA — database access and repository operations
Hibernate — object-relational mapping (ORM)
MariaDB — relational database
Maven — dependency management and build tool
Postman — API testing


## 3. Installation and Setup

**Prerequisites**

Before you can run the application you must install:
* Java 21
* MariaDB server
* Maven
* An IDE (such as Intellij IDEA or VSCode)
* Postman for testing API endpoints


**Database Setup**
1. Start the MariaDB server
2. Import the project database
    * [Link](tobereleased)
3. Ensure that the database schema and required tables exist.
4. Configure the database connection in your project as described in the next section.



## 4. Configuration
The database connection is configured in the project in **`src\main\resources\application.yaml`**.

The application.yaml should look like below:
```yaml
spring:
  jpa:
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        ddl-auto: validate

  application:
    name: demo

  datasource:
    url: jdbc:mariadb://localhost:3306/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

Set the `DB_NAME` to match your database name as well as `DB_USERNAME` and `DB_PASSWORD` to match your credentials.

You should preferably use a dedicated database account for the application rather than the MariaDB root account.



## 5. Running the Application.

1. Start MariaDB.
2. Ensure that database credentials are configurated.
3. Open the project and find the file: `src\main\java\fi\metropolia\arazm\demo\DemoApplication.java`
4. You can start the application from your IDE.

**Alternative method: using the terminal**
1. Open the terminal in the project directory.
2. Start the application using the Maven Wrapper:
```bat
./mvnw spring-boot:run
```
On Windows, use:
```bat
mvnw.cmd spring-boot:run
```

When the Application starts successfully, the REST API is available at the configured host and port. By default Spring Boot port is 8080. You can change these values in the `application.yaml` file.



## 6. System Architecture

**The application follows a layered architecture:**

* **Controller layer:** Receives HTTP requests and returns HTTP responses.
* **Service layer:** Contains application logic and transaction management.
* **Repository layer:** Uses Spring Data JPA to access the database.
* **Entity layer:** Represents database tables as Java objects.
* **DTO layer:** Defines the data transferred through API requests and responses.
* **Exception handling:** Provides centralized handling of supported application errors.

An example of typical request flow:


Typical request flow:
`Client → Controller → Service → Repository → MariaDB`

The response returns through the same layers to the client.



## 7. Project Structure
The application uses packages to separate responsibilities and make the project overall more clear. The main package is within `/demo` folder.



## 8. API Overview
The REST API exposes endpoints for working with webshop resources.

The main resource areas include:
* Products
* Customers
* Suppliers
* Orders
* Categories

The API uses HTTP methods such as GET, POST, PUT, and DELETE, depending on the operations supported by each controller.

The API endpoints are more thorougly explained below in this documentation.

---

## 9. Database Features

### Indexes and Query Optimization

#### Indexes
Database queries are optimized using indexes for foreign keys, frequently searched fields, and composite filtering/sorting needs. Query performance and index usage were verified using the MariaDB EXPLAIN command.


Some examples of the indexes in the database:

| Table | Index Name | Column(s) | Type / Purpose |
| :--- | :--- | :--- | :--- |
| **orders** | `idx_orders_customer_date` | `(customer_id, order_date)` | Composite index for filtering by customer and sorting by date |
| **products** | `fk_product_category` | `(category_id)` | Foreign key index for category lookup |
| **products** | `idx_products_name` | `(name)` | Search index for product name |
| **customers** | `idx_sukunimi` | `(last_name)` | Search index for last name |
| **customers** | `idx_customers_email` | `(email)` | Unique/search index for fast email lookup (+100k rows) |
| **suppliers** | `idx_suppliers_name` | `(name)` | Search index for supplier name |


#### Pagination for Query Optimization
In addition to indexing, pagination is used to optimize database performance—especially for large tables such as `customers` (+100k rows). Fetching an entire dataset in a single request leads to high memory usage, heavy network overhead, and slow API response times. 

By applying page-based limits (translated to SQL `LIMIT` and `OFFSET` clauses), the database only scans, retrieves, and serializes a small subset of records at a time, drastically reducing resource consumption and improving response times.

**Example of Customers Endpoint with Pagination**

* **Default (First 20 customers):**
    - `GET /api/customer/paged`
* **Next page (20 customers):**
    - `GET /api/customer/paged?page=1&size=20`
* **Custom page size (50 customers):**
    - `GET /api/customer/paged?page=0&size=50`




### Database Views
The database contains view `nakyma2`.

The view combines customer information with customer address information to provide a reusable and less exposing query result.

The view can control which columns are exposed to a database user and they do not automatically store a separate copy of the underlying data.



### Triggers and Price History

The database contains an AFTER UPDATE trigger named trg_price_change on the products table.

When a product's price changes, the trigger inserts a history record into price_log containing:

* Product ID
* Old price
* New price
* Change timestamp

The trigger was tested by updating a product price through the REST API using Postman and verifying that the change appeared in price_log.



### Transactions and Concurrency

Spring's `@Transactional` annotation is used to manage database operations that should execute within a transaction.

Transactions help ensure that related operations succeed or fail together. The order creation process validates the referenced customer, address, and products before saving the order.

The Orders entity also uses JPA's `@Version` field for optimistic locking. This helps detect conflicting updates when multiple transactions modify the same order.



### Validation

Request DTOs use Jakarta Bean Validation annotations to check incoming data.

Examples include:
* @NotNull — requires a value
* @NotBlank — requires non-blank text
* @Positive — requires a positive number
* @PositiveOrZero — allows zero or a positive number
* @Size — restricts the length of text

Controllers use @Valid to trigger validation of request bodies.



### Temporal Data

The price_log table preserves a history of product price changes.

The **trg_price_change** trigger records the previous and new prices along with the timestamp of each change. This allows earlier prices to be inspected after a product has been updated.

This is a trigger-based history table rather than a MariaDB system-versioned temporal table.



### Database Security

The application connects to MariaDB using dedicated service accounts rather than the administrative `root` user, adhering to the principle of least privilege. Permissions are scoped strictly to the `tkr_harjoitus_1` database.


| User Account | Access Level | Granted Permissions | Purpose |
| :--- | :--- | :--- | :--- |
| **`tkr_read_user`** | Read-Only | `SELECT` | Restricted read access for reporting or read-only services |
| **`tkr_app_user`** | Read/Write | `SELECT`, `INSERT`, `UPDATE` | Standard application user for handling business operations without structural DB changes |
| **`tkr_app_admin`** | Full Control | `ALL PRIVILEGES` | Administrative account for database setup, migrations, and schema modifications |



---

## 10. Error Handling
The application uses a centralized GlobalExceptionHandler annotated with @RestControllerAdvice.

It handles selected exceptions and returns structured HTTP error responses.

Examples include:

* **400 Bad Request** - invalid request body or enum value
* **404 Not Found** - requested resource does not exist when the service throws a corresponding ResponseStatusException
* **409 Conflict** - optimistic locking conflict