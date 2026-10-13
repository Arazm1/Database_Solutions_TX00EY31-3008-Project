package fi.metropolia.arazm.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.Orders;
import fi.metropolia.arazm.demo.repository.CustomerRepository;

@RestController 
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository){
        this.repository = repository;
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getTiliById(@PathVariable Integer id){
        return repository.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElse(ResponseEntity.notFound().build());
    }
            */

    // GET kaikkien asiakkaiden hakuun: GET /api/customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // GET yhteen asiakkaaseen: GET /api/customers/1
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // POST uuden asiakkaalle ja sen tilauksille: POST /api/customers
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        // Kiinnitetään asiakasviite jokaiseen tilaukseen, jotta FK tallentuu oikein
        if (customer.getOrders() != null) {
            for (Orders order : customer.getOrders()) {
                order.setCustomer(customer);
            }
        }
        return repository.save(customer);
    }

    
}
