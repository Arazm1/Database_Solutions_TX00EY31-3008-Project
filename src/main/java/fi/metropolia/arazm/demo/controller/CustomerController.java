package fi.metropolia.arazm.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.repository.CustomerRepository;

@RestController 
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository){
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getTiliById(@PathVariable Long id){
        return repository.findById(id)
            .map(c -> ResponseEntity.ok(c))
            .orElse(ResponseEntity.notFound().build());
    }

    
}
