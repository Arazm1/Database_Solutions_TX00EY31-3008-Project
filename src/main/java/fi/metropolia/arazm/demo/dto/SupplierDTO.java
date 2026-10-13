package fi.metropolia.arazm.demo.dto;

public record SupplierDTO(
    Integer id,
    String name,
    String contactName,
    String phone,
    String email,
    SupplierAddressDTO supplierAddress
) {}