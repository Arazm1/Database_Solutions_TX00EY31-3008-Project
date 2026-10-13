package fi.metropolia.arazm.demo.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable 
public class OrderItemId implements Serializable {

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "product_id")
    private Integer productId;

    public OrderItemId(){}

    public OrderItemId(Integer orderId, Integer productId){
        this.orderId = orderId;
        this.productId = productId;
    }

    public Integer getOrderID(){
        return this.orderId;
    }
    
    public Integer getProductID(){
        return this.productId;
    }

    public void setOrderID(Integer orderId){
        this.orderId = orderId;
    }

    public void setProductID(Integer productId){
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
        OrderItemId that = (OrderItemId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }


    @Override
    public int hashCode(){
        return Objects.hash(orderId, productId);
    }
    
}
