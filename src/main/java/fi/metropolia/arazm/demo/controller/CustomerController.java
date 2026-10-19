package fi.metropolia.arazm.demo.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.Customer.CustomerAllDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerPageResponse;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.service.CustomerService;

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
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){

        return service.getPagedCustomers(page, size);
    }



    /**
     * GET /api/customer/{id}
     * 
     * Retrieves a specific customer with given id.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id){
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    // 
    /**
     * POST /api/customers
     * 
     * Create new customer with their orders.
     */
    @PostMapping
    public CustomerDTO createCustomer(@RequestBody Customer customer){
        return service.createCustomer(customer);
    }

    
}
