package fi.metropolia.arazm.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.metropolia.arazm.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
