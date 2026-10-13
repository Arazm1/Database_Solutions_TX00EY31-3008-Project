package fi.metropolia.arazm.demo.repository;

import fi.metropolia.arazm.demo.entity.SupplierAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierAddressRepository extends JpaRepository<SupplierAddress, Integer> {
}