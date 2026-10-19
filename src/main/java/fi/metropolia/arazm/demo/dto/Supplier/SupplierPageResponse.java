package fi.metropolia.arazm.demo.dto.supplier;

import java.util.List;

public record SupplierPageResponse(
    List<SupplierDTO> suppliers,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
