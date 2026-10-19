package fi.metropolia.arazm.demo.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.metropolia.arazm.demo.dto.order.OrderDTO;
import fi.metropolia.arazm.demo.dto.order.OrderItemDTO;
import fi.metropolia.arazm.demo.entity.OrderItem;
import fi.metropolia.arazm.demo.entity.Orders;

@Component 
public class OrderMapper {

    public OrderDTO toDto(Orders o){
        List<OrderItemDTO> items = o.getOrderItems().stream()
                .map(this::toDto)
                .toList();

        return new OrderDTO(
            o.getId(),
            o.getStatus(),
            o.getOrderDate(),
            o.getDeliveryDate(),
            o.getCustomer() != null ? o.getCustomer().getId() : null,
            o.getShippingAddress() != null ? o.getShippingAddress().getId() : null,
            items
        );
    }

    public OrderItemDTO toDto(OrderItem i) {
        return new OrderItemDTO(
                i.getProduct().getId(),
                i.getProduct().getName(),
                i.getQuantity(),
                i.getUnitPrice()
        );
    }
    
}
