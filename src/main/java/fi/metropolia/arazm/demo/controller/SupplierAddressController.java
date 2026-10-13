package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.entity.SupplierAddress;
import fi.metropolia.arazm.demo.repository.SupplierAddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-addresses")
public class SupplierAddressController {

    private final SupplierAddressRepository addressRepository;

    public SupplierAddressController(SupplierAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GetMapping
    public List<SupplierAddress> getAll() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierAddress> getById(@PathVariable Integer id) {
        return addressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SupplierAddress create(@RequestBody SupplierAddress address) {
        return addressRepository.save(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!addressRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        addressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}