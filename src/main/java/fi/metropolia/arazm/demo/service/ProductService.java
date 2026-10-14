package fi.metropolia.arazm.demo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import fi.metropolia.arazm.demo.dto.ProductDTO;
import fi.metropolia.arazm.demo.dto.ProductRequest;
import fi.metropolia.arazm.demo.entity.Product;
import fi.metropolia.arazm.demo.entity.ProductCategory;
import fi.metropolia.arazm.demo.entity.Supplier;
import fi.metropolia.arazm.demo.mapper.ProductMapper;
import fi.metropolia.arazm.demo.repository.ProductCategoryRepository;
import fi.metropolia.arazm.demo.repository.ProductRepository;
import fi.metropolia.arazm.demo.repository.SupplierRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository,
                          ProductCategoryRepository categoryRepository,
                          SupplierRepository supplierRepository,
                          ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Integer id) {
        return mapper.toDto(find(id));
    }

    @Transactional
    public ProductDTO create(ProductRequest req) {
        Product p = new Product();
        apply(p, req);
        return mapper.toDto(productRepository.save(p));
    }

    @Transactional
    public ProductDTO update(Integer id, ProductRequest req) {
        Product p = find(id);
        apply(p, req);
        return mapper.toDto(p);   // dirty checking saves the changes at commit
    }

    @Transactional
    public void delete(Integer id) {
        if (!productRepository.existsById(id)) {
            throw notFound("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private Product find(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> notFound("Product not found: " + id));
    }

    private ResponseStatusException notFound(String message) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, message);
    }

    private void apply(Product p, ProductRequest req) {
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setStockQuantity(req.getStockQuantity());

        ProductCategory category = null;
        if (req.getCategoryId() != null) {
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> notFound("Category not found: " + req.getCategoryId()));
        }
        p.setCategory(category);

        Supplier supplier = null;
        if (req.getSupplierId() != null) {
            supplier = supplierRepository.findById(req.getSupplierId())
                    .orElseThrow(() -> notFound("Supplier not found: " + req.getSupplierId()));
        }
        p.setSupplier(supplier);
    }
    
}