package fi.metropolia.arazm.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fi.metropolia.arazm.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{



    @Query(value = """
            SELECT
                customer_id AS customerId,
                last_name AS lastName,
                first_name AS firstName,
                email,
                country
            FROM nakyma2
            """, nativeQuery = true)
    List<CustomerContactProjection> findCustomerContacts();
    
}
