package fi.metropolia.arazm.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fi.metropolia.arazm.demo.entity.Orders;

@Repository 
public interface OrdersRepository extends JpaRepository<Orders, Integer>{
    List<Orders> findByCustomerId(Integer customerId);
}
