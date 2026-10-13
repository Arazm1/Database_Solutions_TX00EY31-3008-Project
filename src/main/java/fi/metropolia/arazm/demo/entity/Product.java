package fi.metropolia.arazm.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    
    @Column(name = "price")
    @Convert(converter = fi.metropolia.arazm.demo.converter.CurrencyConverter.class)
    private Double price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;


    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems = new HashSet<>();

    public Product() {}

    public Product(String name, String description, Double price, Integer stockQuantity, ProductCategory productCategory, Supplier supplier) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.productCategory = productCategory;
        this.supplier = supplier;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Double getPrice() {
        return this.price;
    }

    public Integer getStockQuantity() {
        return this.stockQuantity;
    }

    public ProductCategory getCategory() {
        return this.productCategory;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    //public Set<Orders> getOrders() {
    //    return this.orders;
    //}



    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Set<OrderItem> getOrderItems() { return orderItems; }

    //public void setOrders(Set<Orders> orders) {
    //    this.orders = orders;
    //}
}