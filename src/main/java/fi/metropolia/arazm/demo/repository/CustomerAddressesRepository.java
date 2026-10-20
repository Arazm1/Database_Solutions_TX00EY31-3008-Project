package fi.metropolia.arazm.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.metropolia.arazm.demo.entity.CustomerAddresses;

public interface CustomerAddressesRepository extends JpaRepository<CustomerAddresses, Integer>{
    List<CustomerAddresses> findByCustomerId(Integer customerId);

    void deleteByCustomerId(Integer customerId);
}
