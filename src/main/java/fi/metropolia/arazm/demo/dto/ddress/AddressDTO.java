package fi.metropolia.arazm.demo.dto.ddress;

public record AddressDTO(
    Integer id,
    String streetAddress,
    String postalCode,
    String city,
    String country,
    Integer customerId
) {}
