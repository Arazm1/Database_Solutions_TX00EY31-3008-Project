package fi.metropolia.arazm.demo.dto.product;

import fi.metropolia.arazm.demo.dto.category.ProductCategoryDTO;
import fi.metropolia.arazm.demo.dto.supplier.SupplierDTO;

public record ProductDTO(
    Integer id,
    String name,
    String description,
    Double price,
    ProductCategoryDTO category,
    SupplierDTO supplier
) {}