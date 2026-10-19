package fi.metropolia.arazm.demo.dto.ustomer;

import java.util.List;

public record CustomerPageResponse(
    List<CustomerAllDTO> customers,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
