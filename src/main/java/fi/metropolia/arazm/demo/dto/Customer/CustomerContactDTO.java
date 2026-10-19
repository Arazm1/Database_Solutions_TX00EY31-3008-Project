package fi.metropolia.arazm.demo.dto.Customer;

/**
 * CustomerContactDTO
 * 
 * For nakyma2
 */
public record CustomerContactDTO(
    Integer customerId,
    String lastName,
    String firstName,
    String email,
    String country
) {}
