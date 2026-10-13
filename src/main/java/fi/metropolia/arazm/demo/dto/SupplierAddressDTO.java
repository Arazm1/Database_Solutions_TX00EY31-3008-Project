package fi.metropolia.arazm.demo.dto;

public record SupplierAddressDTO(
    Integer id,
    String streetAddress,
    String postalCode,
    String city,
    String country
) {}