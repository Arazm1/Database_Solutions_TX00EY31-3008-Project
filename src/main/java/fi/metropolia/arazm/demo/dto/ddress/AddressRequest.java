package fi.metropolia.arazm.demo.dto.ddress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequest(
    @NotNull Integer customerId,
    @NotBlank @Size(max = 255) String streetAddress,
    @NotBlank @Size(max = 20) String postalCode,
    @NotBlank @Size(max = 100) String city,
    @NotBlank @Size(max = 100) String country
) {}
