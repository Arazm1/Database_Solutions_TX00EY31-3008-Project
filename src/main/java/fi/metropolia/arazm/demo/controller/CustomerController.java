package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.dto.Customer.CustomerAllDTO;
import fi.metropolia.arazm.demo.dto.Customer.CustomerDTO;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.mapper.CustomerMapper;
import fi.metropolia.arazm.demo.repository.CustomerRepository;

@RestController 
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository){
        this.repository = repository;
    }


    // GET kaikkien asiakkaiden hakuun: GET /api/customers
    @GetMapping 
    @Transactional(readOnly = true)
    public List<CustomerAllDTO> getAllCustomers(){
        return repository.findAll().stream()
                .map(customer -> new CustomerAllDTO(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getPhone()
                ))
                .toList();
    }
    
    /*
    
    @GetMapping
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return repository.findAll().stream()
                .map(CustomerMapper::toDto)
                .toList();
    }
                 

    @GetMapping
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {

        List<Customer> customers = repository.findAll();

        System.out.println(">>> CUSTOMERS FOUND: " + customers.size());

        return customers.stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPhone(),
                        List.of()
                ))
                .toList();
    }
                */

    // GET yhteen asiakkaaseen: GET /api/customers/1
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return repository.findById(id)
            .map(CustomerMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST uuden asiakkaalle ja sen tilauksille: POST /api/customers
    @PostMapping
    @Transactional
    public CustomerDTO createCustomer(@RequestBody Customer customer) {
        // Kiinnitetään asiakasviite jokaiseen tilaukseen, jotta FK tallentuu oikein
        if (customer.getOrders() != null) {
            for (Orders order : customer.getOrders()) {
                order.setCustomer(customer);
            }
        }
        Customer savedCustomer = repository.save(customer);
        return CustomerMapper.toDto(savedCustomer);
    }

    
}
