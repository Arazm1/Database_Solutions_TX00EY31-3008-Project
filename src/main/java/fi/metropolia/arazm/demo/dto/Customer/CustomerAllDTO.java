package fi.metropolia.arazm.demo.dto.Customer;

/**
 *
 * DTO that returns all Customers with their parameters excluding their orders.
 */
public record CustomerAllDTO(
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone
) {}
