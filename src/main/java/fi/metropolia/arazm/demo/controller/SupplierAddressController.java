package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.supplieraddresses.SupplierAddressRequest;
import fi.metropolia.arazm.demo.entity.SupplierAddress;
import fi.metropolia.arazm.demo.repository.SupplierAddressRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.repository.SupplierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier-addresses")
public class SupplierAddressController {

    private final SupplierAddressRepository addressRepository;
    private final SupplierRepository supplierRepository;

    public SupplierAddressController(
            SupplierAddressRepository addressRepository,
            SupplierRepository supplierRepository) {
        this.addressRepository = addressRepository;
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<SupplierAddress> getAllSupplierAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SupplierAddress> getById(@PathVariable @Positive Integer id) {
        return addressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public SupplierAddress create(@Valid @RequestBody SupplierAddressRequest request) {

        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Supplier not found: " + request.supplierId()));

        SupplierAddress address = new SupplierAddress();
        address.setStreetAddress(request.streetAddress());
        address.setPostalCode(request.postalCode());
        address.setCity(request.city());
        address.setCountry(request.country());
        address.setSupplier(supplier);

        return addressRepository.save(address);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SupplierAddress> update(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody SupplierAddressRequest request) {

        return addressRepository.findById(id)
                .map(address -> {
                    address.setStreetAddress(request.streetAddress());
                    address.setPostalCode(request.postalCode());
                    address.setCity(request.city());
                    address.setCountry(request.country());

                    SupplierAddress saved = addressRepository.save(address);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable @Positive Integer id) {
        if (!addressRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        addressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}