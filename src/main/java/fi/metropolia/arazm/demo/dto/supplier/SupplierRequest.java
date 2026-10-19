package fi.metropolia.arazm.demo.dto.supplier;

import fi.metropolia.arazm.demo.dto.supplieraddresses.SupplierAddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
    @NotBlank(message = "Company name is required")
    @Size(max = 255, message = "Company name must not exceed 100 characters")
    String companyName,

    @Size(max = 255, message = "Contact name must not exceed 100 characters")
    String contactName,

    @Email(message = "Must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 100 characters")
    String email,

    @Size(max = 30, message = "Phone number must not exceed 20 characters")
    String phone,

    @Valid
    SupplierAddressRequest supplierAddress
) {}