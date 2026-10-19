package fi.metropolia.arazm.demo.dto.Customer;

import java.util.List;

public record CustomerPageResponse(
    List<CustomerAllDTO> customers,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
