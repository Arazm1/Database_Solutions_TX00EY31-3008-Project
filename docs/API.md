# API Documentation

## Table of Contents

1. [Overview](#overview)
2. [Products](#products)
3. [Customers](#customers)
4. [Suppliers](#suppliers)
5. [Orders](#orders)
6. [Error Responses](#error-responses)

---

## 1. Overview

This document contains the REST API endpoints provided by the Webshop REST API.

Base URL:

```text
http://localhost:8080
```

---

## 2. Products

### GET All Products
**Endpoint:**
* `GET /api/products`

### GET Products with Pagination
**Endpoint:**
* `GET /api/products/paged`

**Query Parameters:**
- `page`
- `size`

Example:
```bat
GET /api/products/paged?page=1&size=20
```

### GET Product by ID
**Endpoint:**
* `GET /api/products/{id}`

### POST Create Product
**Endpoint:**
* `POST /api/products`

Content-Type:
`application/json`

Requested Body:
```json
{
  "name": "Quantum Widget 457",
  "description": "A sample product",
  "price": 849.99,
  "stockQuantity": 10,
  "categoryId": 3,
  "supplierId": 17
}
```


### PUT Update Product
**Endpoint:**
* `PUT /api/products/{id}`

### DELETE Product
**Endpoint:**
* `DELETE /api/products/{id}`


### GET Product by Category

**Endpoint:**
TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO


---

## 3. Customers

### GET All Customers

**Endpoint:**


### GET Customer by ID

**Endpoint:**


### POST Create Customer

**Endpoint:**


### PUT Update Customer

**Endpoint:**


### DELETE Customer

**Endpoint:**


### GET Customer Contacts

**Endpoint:**


---

## 4. Suppliers

### GET All Suppliers

**Endpoint:**


### GET Supplier by ID

**Endpoint:**


### POST Create Supplier

**Endpoint:**


### PUT Update Supplier

**Endpoint:**


### DELETE Supplier

**Endpoint:**


---

## 5. Orders

### GET All Orders

**Endpoint:**


### GET Order by ID

**Endpoint:**


### POST Create Order

**Endpoint:**


### PUT Update Order Status

**Endpoint:**


### DELETE Order

**Endpoint:**


---

## 6. Error Responses

### 400 Bad Request


### 404 Not Found


### 409 Conflict


### 500 Internal Server Error