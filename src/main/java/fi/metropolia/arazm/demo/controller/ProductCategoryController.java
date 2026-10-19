package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.ProductCategoryDTO;
import fi.metropolia.arazm.demo.entity.ProductCategory;
import fi.metropolia.arazm.demo.repository.ProductCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ProductCategoryController {

    private final ProductCategoryRepository categoryRepository;

    public ProductCategoryController(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    /**
     * Converts a ProductCategory entity into a ProductCategoryDTO.
     *
     * @param category the product category entity to convert
     * @return the product category as a DTO
     */
    private ProductCategoryDTO toDto(ProductCategory category) {
        return new ProductCategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    /**
     * GET /api/categories
     * Retrieves all product categories.
     *
     * @return a list of product category DTOs
     */
    @GetMapping
    public List<ProductCategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }


    /**
     * GET /api/categories/{id}
     * Retrieves a Product Category by its ID.
     * @param id the ID of the Product Category.
     * @return the category DTO if found, or HTTP 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getById(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/categories
     * Creates a new product category.
     *
     * @param category the product category data from the request body
     * @return the created product category DTO
     */
    @PostMapping
    public ProductCategoryDTO create(@RequestBody ProductCategory category) {
        ProductCategory saved = categoryRepository.save(category);
        return toDto(saved);
    }


    
    /**
     * PUT /api/categories/{id}
     * Updates an existing product category.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> update(
            @PathVariable Integer id,
            @RequestBody ProductCategory updatedCategory) {

        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    category.setDescription(updatedCategory.getDescription());

                    ProductCategory saved = categoryRepository.save(category);
                    return ResponseEntity.ok(toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * DELETE /api/categories/{id}
     * Deletes a product category by its ID.
     * 
     * @param id of the product category to be deleted.
     * @return HTTP 204 No Content if deleted, or HTTP 404 Not Found if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}