# API Documentation

## Table of Contents

1. [Overview](#overview)
2. [Products](#products)
3. [Customers](#customers)
4. [Suppliers](#suppliers)
5. [Orders](#orders)
6. [Addresses](#addresses)
7. [Categories](#categories)
8. [Supplier Addresses](#supplier-addresses)
9. [Error Responses](#error-responses)

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

Content-Type:
`application/json`

Requested Body:
```json
{
  "name": "Quantum Widget 457 MKII",
  "description": "A sample product",
  "price": 949.99,
  "stockQuantity": 10,
  "categoryId": 3,
  "supplierId": 17
}
```

### DELETE Product
**Endpoint:**
* `DELETE /api/products/{id}`




---

## 3. Customers

### GET All Customers

**Endpoint:**
`GET /api/customer`

**Request Body:**
None.

---

### GET Customers with Pagination

**Endpoint:**
`GET /api/customer/paged`

**Query Parameters:**
- `page` - page number, minimum `0`, default `0`
- `size` - page size, `1–100`, default `20`

**Example:**
`GET /api/customer/paged?page=0&size=20`

**Request Body:**
None.

---

### GET Customer by ID

**Endpoint:**
`GET /api/customer/{id}`

**Path Parameter:**
- `id` - customer ID, must be positive

**Example:**
`GET /api/customer/1`

**Request Body:**
None.

---

### GET Customer Contacts

**Endpoint:**
`GET /api/customer/contacts`

Returns customer contact information from the database view `nakyma2`.

**Request Body:**
None.

---

### POST Create Customer

**Endpoint:**
`POST /api/customer`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "firstName": "Matti",
  "lastName": "Meikäläinen",
  "email": "matti.meikalainen@example.com",
  "phone": "+358 40 123 4567"
}
```


---

### PUT Update Customer

**Endpoint:**
`PUT /api/customer/{id}`

**Path Parameter:**
- `id` - customer ID, must be positive

**Example:**
`PUT /api/customer/1`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "firstName": "Matti",
  "lastName": "Meikäläinen",
  "email": "matti.updated@example.com",
  "phone": "+358 40 987 6543"
}
```


### DELETE Customer

**Endpoint:**
`DELETE /api/customer/{id}`

**Path Parameter:**
- `id` - customer ID, must be positive

**Example:**
`DELETE /api/customer/1`

**Request Body:**
None.






---

## 4. Suppliers

### GET All Suppliers

**Endpoint:**
`GET /api/suppliers`

**Request Body:** None.

---

### GET Suppliers with Pagination

**Endpoint:**
`GET /api/suppliers/paged`

**Query Parameters:**
- `page` — page number, default `0`
- `size` — number of suppliers per page, default `20`

**Example:**
`GET /api/suppliers/paged?page=0&size=20`

**Request Body:** None.

---

### GET Supplier by ID

**Endpoint:**
* `GET /api/suppliers/{id}`

**Request Body:** None.

---

### POST Create Supplier

**Endpoint:**
* `POST /api/suppliers`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "companyName": "Nordic Supplies Oy",
  "contactName": "Matti Meikäläinen",
  "phone": "+358 40 123 4567",
  "email": "matti@example.com",
  "supplierAddress": {
    "streetAddress": "Example Street 10",
    "postalCode": "00100",
    "city": "Helsinki",
    "country": "Finland"
  }
}
```

---

### PUT Update Supplier

**Endpoint:**
* `PUT /api/suppliers/{id}`


**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "companyName": "Nordic Supplies Oy",
  "contactName": "Anna Virtanen",
  "phone": "+358 40 987 6543",
  "email": "anna@example.com"
}
```

**Note:** This endpoint updates the supplier's name, contact name, phone, and email. It does not update the supplier address in the current service implementation.

---

### DELETE Supplier

**Endpoint:**
* `DELETE /api/suppliers/{id}`

**Request Body:** None.


---

## 5. Orders

### GET All Orders

**Endpoint:**
* `GET /api/orders`

**Request Body:** None.

---

### GET Orders with Pagination

**Endpoint:**
* `GET /api/orders/paged`

**Query Parameters:**
- `page` — page number, minimum `0`, default `0`
- `size` — number of orders per page, range `1–100`, default `20`

**Example:**
`GET /api/orders/paged?page=0&size=20`

**Request Body:** None.

---

### GET Order by ID

**Endpoint:**
* `GET /api/orders/{id}`

**Path Parameter:**
- `id` - positive order ID

**Request Body:** None.

---

### GET Orders by Customer

**Endpoint:**
* `GET /api/orders/customer/{id}`

**Path Parameter:**
- `id` — positive customer ID


Returns the orders belonging to the specified customer.

**Request Body:** None.

---

### POST Create Order

**Endpoint:**
* `POST /api/orders`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "customerId": 1,
  "shippingAddressId": 1,
  "items": [
    {
      "productId": 3,
      "quantity": 2
    },
    {
      "productId": 5,
      "quantity": 1
    }
  ]
}
```

**Fields:**
- `customerId` — positive ID of an existing customer
- `shippingAddressId` — positive ID of an existing shipping address
- `items` — non-empty list of order items
- `productId` — positive ID of an existing product
- `quantity` — positive quantity for each item

The order status is initially set to `NEW`.

---

### PUT Update Order Status

**Endpoint:**
* `PUT /api/orders/{id}/status`

**Path Parameter:**
- `id` - positive order ID

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "status": "SHIPPED"
}
```

**Valid status values:** Use the constants defined in `OrderStatus` enum such `NEW`, `SHIPPED` or `CANCELLED`.

---

### DELETE Order

**Endpoint:**
* `DELETE /api/orders/{id}`

**Path Parameter:**
- `id` - positive order ID


**Request Body:** None.



## 6. Addresses

### GET All Addresses

**Endpoint:**
* `GET /api/addresses`

**Request Body:** None.

---

### GET Address by ID

**Endpoint:**
* `GET /api/addresses/{id}`


**Request Body:** None.

**Responses:**
- `200 OK` — address found.
- `404 Not Found` — address does not exist.

---

### GET Addresses by Customer

**Endpoint:**
`GET /api/addresses/customer/{customerId}`


**Path Parameter:**
- `customerId` — ID of the customer.

**Request Body:** None.

---

### POST Create Address

**Endpoint:**
`POST /api/addresses`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "streetAddress": "Example Street Address",
  "postalCode": "00100",
  "city": "Helsinki",
  "country": "Finland",
  "customer": {
    "id": 1
  }
}
```

**Fields:**
- `streetAddress` — street address.
- `postalCode` — postal code.
- `city` — city.
- `country` — country.
- `customer.id` — ID of the customer associated with the address.

---

### PUT Update Address

**Endpoint:**
* `PUT /api/addresses/{id}`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "streetAddress": "Example Street 100",
  "postalCode": "00100",
  "city": "Helsinki",
  "country": "Finland"
}
```

**Responses:**
- `200 OK` — address updated successfully.
- `404 Not Found` — address does not exist.

---

### DELETE Address

**Endpoint:**
`DELETE /api/addresses/{id}`

**Request Body:** None.

**Responses:**
- `204 No Content` — address deleted successfully.
- `404 Not Found` — address does not exist.


---


## 7. Categories

### GET All Categories

**Endpoint:**
`GET /api/categories`

**Request Body:** None.

---

### GET Category by ID

**Endpoint:**
* `GET /api/categories/{id}`

**Request Body:** None.

**Responses:**
- `200 OK` — category found.
- `404 Not Found` — category does not exist.

---

### POST Create Category

**Endpoint:**
* `POST /api/categories`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "name": "Electronics",
  "description": "Electronic devices and accessories"
}
```

**Fields:**
- `name` — category name.
- `description` — category description.

---

### PUT Update Category

**Endpoint:**
* `PUT /api/categories/{id}`

**Content-Type:**
`application/json`

**Request Body:**
```json
{
  "name": "Updated Electronics",
  "description": "Updated category description"
}
```

**Responses:**
- `200 OK` — category updated successfully.
- `404 Not Found` — category does not exist.

---

### DELETE Category

**Endpoint:**
* `DELETE /api/categories/{id}`

**Request Body:** None.

**Responses:**
- `204 No Content` — category deleted successfully.
- `404 Not Found` — category does not exist.





## 8. Supplier Addresses

### GET All Supplier Addresses

**Endpoint:**
* `GET /api/supplier-addresses`

**Request Body:** None.

---

### GET Supplier Address by ID

**Endpoint:**
* `GET /api/supplier-addresses/{id}`


**Request Body:** None.

**Responses:**
- `200 OK` — supplier address found.
- `404 Not Found` — supplier address does not exist.

---

### POST Create Supplier Address

**Endpoint:**
* `POST /api/supplier-addresses`

**Content-Type:**
`application/json`

**Request Body:**
```json id="a1b2c3"
{
  "streetAddress": "Hamngatan 12",
  "postalCode": "211 22",
  "city": "Malmö",
  "country": "Sweden",
  "supplierId": 17
}
```

**Fields:**
- `streetAddress` — street address.
- `postalCode` — postal code.
- `city` — city.
- `country` — country.
- `supplierId` — positive ID of the associated supplier.

---


### Update Supplier Address

Updates an existing supplier address.

**Endpoint:** 
* `PUT /api/supplier-addresses/{id}`

**Path parameter:**
- `id` - ID of the supplier address to update.

**Request body:**

```json
{
  "streetAddress": "Hamngatan 25",
  "postalCode": "211 22",
  "city": "Malmö",
  "country": "Sweden"
}
```

**Success response:** `200 OK`

Returns the updated supplier address.

**Error responses:**
- `404 Not Found` — No supplier address exists with the specified ID.

The update changes the address fields while preserving the existing address ID and supplier relationship.

---



### DELETE Supplier Address

**Endpoint:**
`DELETE /api/supplier-addresses/{id}`


**Request Body:** None.

**Responses:**
- `204 No Content` — supplier address deleted successfully.
- `404 Not Found` — supplier address does not exist.



## 9. Error Responses

The API uses a centralized exception handler (**GlobalExceptionHandler**) to guarantee consistent error formats across all endpoints. All error responses return a JSON object containing `status`, `error`, and `message` fields.

### 400 Bad Request
```json
{
  "status": "400",
  "error": "Bad Request",
  "message": "email: Must be a valid email address; phone: Phone number is required"
}
```

### 404 Not Found
Returned when a requested resource does not exist in the database.

```json
{
  "status": "404",
  "error": "Not Found",
  "message": "Customer not found with an ID 999"
}
```

### 409 Conflict

Occurs when a request violates database unique constraints or optimistic locking.

```json
{
  "status": "409",
  "error": "Conflict",
  "message": "The operation conflicts with existing data"
}
```


### 500 Internal Server Error
Returned when an unexpected, unhandled server-side exception occurs.
```json
{
  "status": "500",
  "error": "Internal Server Error",
  "message": "An unexpected error occurred on the server"
}
```