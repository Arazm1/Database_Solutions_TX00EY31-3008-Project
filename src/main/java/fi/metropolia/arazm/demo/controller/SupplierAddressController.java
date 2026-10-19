package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.entity.SupplierAddress;
import fi.metropolia.arazm.demo.repository.SupplierAddressRepository;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public List<SupplierAddress> getAllSupplierAddresses() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<SupplierAddress> getById(@PathVariable Integer id) {
        return addressRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public SupplierAddress create(@RequestBody SupplierAddress address) {
        return addressRepository.save(address);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SupplierAddress> update(
            @PathVariable Integer id,
            @RequestBody SupplierAddress updatedAddress) {

        return addressRepository.findById(id)
                .map(address -> {
                    address.setStreetAddress(updatedAddress.getStreetAddress());
                    address.setPostalCode(updatedAddress.getPostalCode());
                    address.setCity(updatedAddress.getCity());
                    address.setCountry(updatedAddress.getCountry());

                    SupplierAddress saved = addressRepository.save(address);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!addressRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        addressRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}