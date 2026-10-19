package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import fi.metropolia.arazm.demo.dto.product.ProductDTO;
import fi.metropolia.arazm.demo.dto.product.ProductPageResponse;
import fi.metropolia.arazm.demo.dto.product.ProductRequest;
import fi.metropolia.arazm.demo.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/paged")
    public ProductPageResponse getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return service.getAllPaged(page, size);
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@Valid @RequestBody ProductRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Integer id, @Valid @RequestBody ProductRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    // JPQL mass update
    //@PutMapping("/update-prices")
    //public ResponseEntity<String> updatePrices(@RequestParam Integer categoryId,
    //                                           @RequestParam Double multiplier) {
    //    int updatedCount = service.updatePrices(categoryId, multiplier);
    //    return ResponseEntity.ok("Päivitetty " + updatedCount + " tuotteen hinnat.");
    //}

    // Criteria API dynamic search
    //@GetMapping("/search")
    //public List<ProductDTO> search(@RequestParam(required = false) Double minPrice,
    //                               @RequestParam(required = false) Integer categoryId) {
    //    return service.search(minPrice, categoryId);
    //}
}