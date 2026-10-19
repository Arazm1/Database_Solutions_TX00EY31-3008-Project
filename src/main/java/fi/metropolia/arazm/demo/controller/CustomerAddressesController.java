package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.dto.ddress.AddressDTO;
import fi.metropolia.arazm.demo.entity.CustomerAddresses;
import fi.metropolia.arazm.demo.repository.CustomerAddressesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/addresses")
public class CustomerAddressesController {

    private final CustomerAddressesRepository repository;

    public CustomerAddressesController(CustomerAddressesRepository repository) {
        this.repository = repository;
    }

    private AddressDTO toDto(CustomerAddresses address) {
        return new AddressDTO(
                address.getId(),
                address.getStreetAddress(),
                address.getPostalCode(),
                address.getCity(),
                address.getCountry(),
                address.getCustomer() == null
                        ? null
                        : address.getCustomer().getId());
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<AddressDTO> getAllAddresses() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable @Positive Integer id) {
        return repository.findById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/addresses/customer/{customerId}
     * Fetch all addresses for a specific customer
     */
    @GetMapping("/customer/{customerId}")
    @Transactional(readOnly = true)
    public List<AddressDTO> getAddressesByCustomerId(@PathVariable @Positive Integer customerId) {
        return repository.findByCustomerId(customerId).stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * POST /api/addresses
     * Create a new address
     */
    @PostMapping
    @Transactional
    public AddressDTO createAddress(@Valid @RequestBody CustomerAddresses address) {
        CustomerAddresses saved = repository.save(address);
        return toDto(saved);
    }

    /**
     * PUT /api/addresses/{id}
     * Update an existing address.
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AddressDTO> updateAddress(
            @PathVariable @Positive Integer id,
            @Valid @RequestBody CustomerAddresses updatedAddress) {

        return repository.findById(id)
                .map(address -> {
                    address.setStreetAddress(updatedAddress.getStreetAddress());
                    address.setPostalCode(updatedAddress.getPostalCode());
                    address.setCity(updatedAddress.getCity());
                    address.setCountry(updatedAddress.getCountry());

                    CustomerAddresses saved = repository.save(address);
                    return ResponseEntity.ok(toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/addresses/{id}
     * Delete an address
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAddress(@PathVariable @Positive Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
