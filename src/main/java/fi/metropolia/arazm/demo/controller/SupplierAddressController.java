package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.supplieraddresses.SupplierAddressDTO;
import fi.metropolia.arazm.demo.dto.supplieraddresses.SupplierAddressRequest;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.entity.SupplierAddress;
import fi.metropolia.arazm.demo.repository.SupplierAddressRepository;
import fi.metropolia.arazm.demo.repository.SupplierRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    private SupplierAddressDTO toDto(SupplierAddress address) {
        return new SupplierAddressDTO(
                address.getId(),
                address.getStreetAddress(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry());
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<SupplierAddressDTO> getAllSupplierAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SupplierAddressDTO> getById(
            @PathVariable @Positive Integer id) {

        return addressRepository.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public SupplierAddressDTO create(
            @Valid @RequestBody SupplierAddressRequest request) {

        if (request.supplierId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Supplier ID is required");
        }

        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Supplier not found: " + request.supplierId()));

        if (supplier.getSupplierAddress() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Supplier already has an address");
        }

        SupplierAddress address = new SupplierAddress();
        address.setStreetAddress(request.streetAddress());
        address.setPostalCode(request.postalCode());
        address.setCity(request.city());
        address.setCountry(request.country());
        address.setSupplier(supplier);
        supplier.setSupplierAddress(address);

        SupplierAddress saved = addressRepository.save(address);
        return toDto(saved);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SupplierAddressDTO> update(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody SupplierAddressRequest request) {

        return addressRepository.findById(id)
                .map(address -> {
                    address.setStreetAddress(request.streetAddress());
                    address.setPostalCode(request.postalCode());
                    address.setCity(request.city());
                    address.setCountry(request.country());

                    SupplierAddress saved = addressRepository.save(address);
                    return ResponseEntity.ok(toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(
            @PathVariable @Positive Integer id) {

        if (!addressRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        addressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}