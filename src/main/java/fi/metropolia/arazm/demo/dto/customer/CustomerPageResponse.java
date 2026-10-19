package fi.metropolia.arazm.demo.dto.customer;

import java.util.List;

public record CustomerPageResponse(
    List<CustomerAllDTO> customers,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
