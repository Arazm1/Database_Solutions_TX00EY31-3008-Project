package fi.metropolia.arazm.demo.archive;

import fi.metropolia.arazm.demo.dto.AddressDTO;
import fi.metropolia.arazm.demo.entity.CustomerAddresses;
import fi.metropolia.arazm.demo.repository.CustomerAddressesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/addresses")
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
                        ? null : address.getCustomer().getId()
        );
    }


    @GetMapping 
    @Transactional(readOnly = true)
    public List<AddressDTO> getAllAddresses(){
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }






    /**
     * GET /api/addresses
     * Fetch all addresses
     */
    //@GetMapping
    //public List<CustomerAddresses> getAllAddresses() {
    //    return repository.findAll();
    //}

    /**
     * GET /api/addresses/{id}
     * Fetch address by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerAddresses> getAddressById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/addresses/customer/{customerId}
     * Fetch all addresses for a specific customer
     */
    @GetMapping("/customer/{customerId}")
    public List<CustomerAddresses> getAddressesByCustomerId(@PathVariable Integer customerId) {
        return repository.findByCustomerId(customerId);
    }

    /**
     * POST /api/addresses
     * Create a new address
     */
    @PostMapping
    public CustomerAddresses createAddress(@RequestBody CustomerAddresses address) {
        return repository.save(address);
    }

    /**
     * DELETE /api/addresses/{id}
     * Delete an address
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
