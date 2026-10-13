package fi.metropolia.arazm.demo.controller;

import fi.metropolia.arazm.demo.entity.Product;
import fi.metropolia.arazm.demo.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PersistenceContext
    private EntityManager entityManager;

    //JPQL Mass update
    @PutMapping("/update-prices")
    public ResponseEntity<String> updatePrices(@RequestParam Integer categoryId, @RequestParam Double multiplier) {
        int updatedCount = productRepository.updatePricesByCategory(categoryId, multiplier);
        return ResponseEntity.ok("Päivitetty " + updatedCount + " tuotteen hinnat.");
    }

    //Criteria API Dynamic search
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) Double minPrice,
                                       @RequestParam(required = false) Integer categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
        }
        if (categoryId != null) {
            predicates.add(cb.equal(product.get("productCategory").get("id"), categoryId));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}