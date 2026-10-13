package fi.metropolia.arazm.demo.dto;

public record ProductDTO(
    Integer id,
    String name,
    String description,
    Double price, // vaihda tyypiksi se mitä käytät Product-entiteetissä (esim. BigDecimal)
    ProductCategoryDTO category,
    SupplierDTO supplier
) {}