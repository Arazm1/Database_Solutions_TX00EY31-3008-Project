package fi.metropolia.arazm.demo.dto.upplier;

import fi.metropolia.arazm.demo.dto.upplieraddresses.SupplierAddressDTO;

public record SupplierDTO(
    Integer id,
    String name,
    String contactName,
    String phone,
    String email,
    SupplierAddressDTO supplierAddress
) {}