package fi.metropolia.arazm.demo.dto.address;

public record AddressDTO(
    Integer id,
    String streetAddress,
    String postalCode,
    String city,
    String country,
    Integer customerId
) {}
