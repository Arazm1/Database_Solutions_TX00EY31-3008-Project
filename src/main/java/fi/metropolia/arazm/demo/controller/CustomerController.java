package fi.metropolia.arazm.demo.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.Customer.CustomerAllDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerContactDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerPageResponse;
import fi.metropolia.arazm.demo.dto.Customer.CustomerRequest;
import fi.metropolia.arazm.demo.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@RestController 
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service){
        this.service = service;
    }


    // GET kaikkien asiakkaiden hakuun: GET /api/customers
    @GetMapping 
    public List<CustomerAllDTO> getAllCustomers(){
        return service.getAllCustomers();
    }
    


    /**
     * GET /api/customer/paged
     * Fetch customers in pages to avoid loading entire customer table at once.
     */
    @GetMapping("/paged")
    public CustomerPageResponse getPagedCustomers(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size){

        return service.getPagedCustomers(page, size);
    }



    /**
     * GET /api/customer/{id}
     * 
     * Retrieves a specific customer with given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable @Positive Integer id){
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    /**
     * GET /api/customer/contacts
     * @return customer contacts from View 'nakyma2'
     */
    @GetMapping("/contacts")
    public List<CustomerContactDTO> getCustomerContacts(){
        return service.getCustomerContacts();
    }

    /**
     * POST /api/customers
     * 
     * Create new customer with their orders.
     */
    @PostMapping
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerRequest req){
        return service.createCustomer(req);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable @Positive Integer id,
                                    @Valid @RequestBody CustomerRequest req) {
        return service.updateCustomer(id, req);
    }



    
}
