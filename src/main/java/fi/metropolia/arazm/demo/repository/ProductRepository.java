package fi.metropolia.arazm.demo.repository;

import fi.metropolia.arazm.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.price = p.price * :multiplier WHERE p.productCategory.id = :categoryId")
    int updatePricesByCategory(@Param("categoryId") Integer categoryId, @Param("multiplier") Double multiplier);
    }