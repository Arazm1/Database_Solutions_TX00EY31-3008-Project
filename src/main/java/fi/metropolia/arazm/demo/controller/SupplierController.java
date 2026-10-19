package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.Supplier.SupplierDTO;
import fi.metropolia.arazm.demo.dto.Supplier.SupplierPageResponse;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.service.SupplierService;

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
     * @return 
     */
    @GetMapping
    public List<SupplierDTO> getAllSuppliers() {
        return service.getAllSuppliers();
    }


    /**
     * GET /api/suppliers/paged
     * 
     * @return
     */
    @GetMapping("/paged")
    public SupplierPageResponse getPagedSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return service.getPagedResponse(page, size);
    }


    /**
     * /api/suppliers/{id}
     * @param id of the Supplier
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getSupplierById(id));
    }

    @PostMapping
    public SupplierDTO create(@RequestBody Supplier supplier) {
        return service.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public SupplierDTO update(
            @PathVariable Integer id,
            @RequestBody Supplier supplier) {
        return service.updateSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}