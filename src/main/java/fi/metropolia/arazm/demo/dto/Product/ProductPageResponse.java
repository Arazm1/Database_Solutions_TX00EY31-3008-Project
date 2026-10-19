package fi.metropolia.arazm.demo.dto.product;

import java.util.List;

public record ProductPageResponse(
    List<ProductDTO> products,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
