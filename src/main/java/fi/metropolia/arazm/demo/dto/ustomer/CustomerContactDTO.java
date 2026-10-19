package fi.metropolia.arazm.demo.dto.ustomer;

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
