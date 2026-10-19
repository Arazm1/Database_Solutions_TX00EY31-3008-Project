package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.supplier.SupplierDTO;
import fi.metropolia.arazm.demo.dto.supplier.SupplierPageResponse;
import fi.metropolia.arazm.demo.dto.supplier.SupplierRequest;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.service.SupplierService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;

    public SupplierController(SupplierService service){
        this.service = service;
    }

    /**
     * GET /api/suppliers
     * Retrieves all suppliers.
     */
    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        return service.getAllSuppliers();
    }


    /**
     * GET /api/suppliers/paged
     * Retrieves a paginated list of suppliers.
     */
    @GetMapping("/paged")
    public SupplierPageResponse getPagedSuppliers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") int size) {

        return service.getPagedResponse(page, size);
    }


    /**
     * GET /api/suppliers/{id}
     * Retrieves a single supplier by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(service.getSupplierById(id));
    }

    /**
     * POST /api/suppliers
     * Creates a new supplier.
     */
    @PostMapping
    public SupplierDTO create(@Valid @RequestBody SupplierRequest request) {
        return service.createSupplier(request);
    }

    /**
     * PUT /api/suppliers/{id}
     * Updates an existing supplier by ID.
     */
    @PutMapping("/{id}")
    public SupplierDTO update(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody SupplierRequest request) {
        return service.updateSupplier(id, request);
    }

    /**
     * DELETE /api/suppliers/{id}
     * Deletes a supplier by ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        service.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}