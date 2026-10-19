package fi.metropolia.arazm.demo.dto.upplieraddresses;

public record SupplierAddressDTO(
    Integer id,
    String streetAddress,
    String postalCode,
    String city,
    String country
) {}