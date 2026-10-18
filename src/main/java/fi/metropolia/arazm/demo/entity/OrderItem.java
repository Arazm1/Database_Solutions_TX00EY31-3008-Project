package fi.metropolia.arazm.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity 
@Table(name = "orderitems")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id = new OrderItemId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    public OrderItem() {}

    public OrderItem(Orders order, Product product, Integer quantity, Double unitPrice) {
        this.orders = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    
    public OrderItemId getId() {
        return this.id;
    }
    
    public Orders getOrder(){
        return this.orders;
    }

    public Product getProduct(){
        return this.product;
    }

    public Integer getQuantity(){
        return this.quantity;
    }

    public Double getUnitPrice(){
        return this.unitPrice;
    }



    public void setId(OrderItemId id) {
        this.id = id;
    }

    public void setOrder(Orders order) {
        this.orders = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public void setUnitPrice(Double unitPrice){
        this.unitPrice = unitPrice;
    }


}
