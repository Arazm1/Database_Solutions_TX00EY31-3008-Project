package fi.metropolia.arazm.demo.dto.Product;

import java.util.List;

import fi.metropolia.arazm.demo.dto.ProductDTO;

public record ProductPageResponse(
    List<ProductDTO> products,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
