package fi.metropolia.arazm.demo.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.metropolia.arazm.demo.dto.Order.OrderDto;
import fi.metropolia.arazm.demo.dto.Order.OrderItemDto;
import fi.metropolia.arazm.demo.entity.OrderItem;
import fi.metropolia.arazm.demo.entity.Orders;

@Component 
public class OrderMapper {

    public OrderDto toDto(Orders o){
        List<OrderItemDto> items = o.getOrderItems().stream()
                .map(this::toDto)
                .toList();

        return new OrderDto(
            o.getId(),
            o.getStatus(),
            o.getOrderDate(),
            o.getDeliveryDate(),
            o.getCustomer() != null ? o.getCustomer().getId() : null,
            o.getShippingAddress() != null ? o.getShippingAddress().getId() : null,
            items
        );
    }

    public OrderItemDto toDto(OrderItem i) {
        return new OrderItemDto(
                i.getProduct().getId(),
                i.getProduct().getName(),
                i.getQuantity(),
                i.getUnitPrice()
        );
    }
    
}
