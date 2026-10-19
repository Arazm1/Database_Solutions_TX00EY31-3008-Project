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

