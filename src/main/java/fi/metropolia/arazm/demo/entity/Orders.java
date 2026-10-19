package fi.metropolia.arazm.demo.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fi.metropolia.arazm.demo.common.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity 
@Table(name = "orders")
public class Orders {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version 
    @Column(name = "version")
    private Long version;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddresses shippingAddressID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();


    public Orders(){}

    public Orders(Customer customer, LocalDateTime orderDate, LocalDateTime deliveryDate, CustomerAddresses shippingAddressID, OrderStatus status) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.shippingAddressID = shippingAddressID;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return this.id;
    }

    public Long getVersion() {
        return this.version;
    }

    public LocalDateTime getOrderDate() {
        return this.orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return this.deliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public CustomerAddresses getShippingAddress() {
        return this.shippingAddressID;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    //public Set<Product> getProducts() {
    //    return this.products;
    //}



    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setShippingAddress(CustomerAddresses shippingAddress) {
        this.shippingAddressID = shippingAddress;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderItem> getOrderItems(){ 
        return this.orderItems;
    }

    public void addItem(Product product, Integer quantity, Double unitPrice) {
        OrderItem item = new OrderItem(this, product, quantity, unitPrice);
        orderItems.add(item);
    }

    public void removeItem(OrderItem item) {
        orderItems.remove(item);
    }





    //public void setProducts(Set<Product> products) {
    //    this.products = products;
    //}
    
}
