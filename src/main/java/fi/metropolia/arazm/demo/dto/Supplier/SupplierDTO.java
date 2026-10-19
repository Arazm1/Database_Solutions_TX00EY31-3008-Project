package fi.metropolia.arazm.demo.dto.Supplier;

import fi.metropolia.arazm.demo.dto.SupplierAddressDTO;

public record SupplierDTO(
    Integer id,
    String name,
    String contactName,
    String phone,
    String email,
    SupplierAddressDTO supplierAddress
) {}