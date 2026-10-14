package fi.metropolia.arazm.demo.dto;

public record ProductDTO(
    Integer id,
    String name,
    String description,
    Double price,
    ProductCategoryDTO category,
    SupplierDTO supplier
) {}