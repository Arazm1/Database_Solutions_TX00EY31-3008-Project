package fi.metropolia.arazm.demo.dto.roduct;

import fi.metropolia.arazm.demo.dto.ategory.ProductCategoryDTO;
import fi.metropolia.arazm.demo.dto.upplier.SupplierDTO;

public record ProductDTO(
    Integer id,
    String name,
    String description,
    Double price,
    ProductCategoryDTO category,
    SupplierDTO supplier
) {}