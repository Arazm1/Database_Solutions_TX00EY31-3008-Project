package fi.metropolia.arazm.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fi.metropolia.arazm.demo.dto.supplier.SupplierDTO;
import fi.metropolia.arazm.demo.dto.supplier.SupplierRequest;
import fi.metropolia.arazm.demo.dto.supplieraddresses.SupplierAddressDTO;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.entity.SupplierAddress;
import fi.metropolia.arazm.demo.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all suppliers.
     * 
     * @return list of Supplier DTOs.
     */
    public List<SupplierDTO> getAllSuppliers() {
        return repository.findAll()
                .stream()
                .map(this::toSupplierDTO)
                .toList();
    }

    public fi.metropolia.arazm.demo.dto.supplier.SupplierPageResponse getPagedResponse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Supplier> supplierPage = repository.findAll(pageable);

        List<SupplierDTO> suppliers = supplierPage.getContent()
                .stream()
                .map(this::toSupplierDTO)
                .toList();

        return new fi.metropolia.arazm.demo.dto.supplier.SupplierPageResponse(
                suppliers,
                supplierPage.getNumber(),
                supplierPage.getSize(),
                supplierPage.getTotalElements(),
                supplierPage.getTotalPages());
    }

    public SupplierDTO getSupplierById(Integer id) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Supplier not found: " + id));

        return toSupplierDTO(supplier);
    }

    /**
     * Creates a new supplier.
     */

    @Transactional
    public SupplierDTO createSupplier(SupplierRequest request) {
        Supplier supplier = new Supplier();

        supplier.setName(request.companyName());
        supplier.setContactName(request.contactName());
        supplier.setPhone(request.phone());
        supplier.setEmail(request.email());

        if (request.supplierAddress() != null) {
            SupplierAddress address = new SupplierAddress();

            address.setStreetAddress(request.supplierAddress().streetAddress());
            address.setPostalCode(request.supplierAddress().postalCode());
            address.setCity(request.supplierAddress().city());
            address.setCountry(request.supplierAddress().country());

            address.setSupplier(supplier);
            supplier.setSupplierAddress(address);
        }

        Supplier savedSupplier = repository.save(supplier);
        return toSupplierDTO(savedSupplier);
    }

    /**
     * Updates an existing supplier.
     * 
     * @param id the ID of the specific Supplier to be updated.
     */
    @Transactional
    public SupplierDTO updateSupplier(Integer id, SupplierRequest request) {
        Supplier supplier = findSupplierById(id);

        supplier.setName(request.companyName());
        supplier.setContactName(request.contactName());
        supplier.setPhone(request.phone());
        supplier.setEmail(request.email());

        return toSupplierDTO(supplier);
    }

    /**
     * Deletes a Supplier by ID.
     * 
     * @param id of the Supplier to be deleted.
     */
    @Transactional
    public void deleteSupplier(Integer id) {
        Supplier supplier = findSupplierById(id);
        repository.delete(supplier);
    }

    /**
     * Helper function
     * 
     * Finds a supplier.
     * 
     * @param id the id of specific Supplier
     * @return the Supplier entity.
     */
    private Supplier findSupplierById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Supplier not found: " + id));
    }

    /**
     * Converts a Supplier entity into a SupplierDTO.
     * 
     * @param supplier the entity.
     * @return the supplier DTO.
     */
    private SupplierDTO toSupplierDTO(Supplier supplier) {
        SupplierAddress address = supplier.getSupplierAddress();

        SupplierAddressDTO addressDTO = null;
        if (address != null) {
            addressDTO = new SupplierAddressDTO(
                    address.getId(),
                    address.getStreetAddress(),
                    address.getPostalCode(),
                    address.getCity(),
                    address.getCountry());
        }

        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContactName(),
                supplier.getPhone(),
                supplier.getEmail(),
                addressDTO);
    }

}
