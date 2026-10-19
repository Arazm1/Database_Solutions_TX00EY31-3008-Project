package fi.metropolia.arazm.demo.dto.Product;

import fi.metropolia.arazm.demo.dto.ProductCategoryDTO;
import fi.metropolia.arazm.demo.dto.Supplier.SupplierDTO;

public record ProductDTO(
    Integer id,
    String name,
    String description,
    Double price,
    ProductCategoryDTO category,
    SupplierDTO supplier
) {}