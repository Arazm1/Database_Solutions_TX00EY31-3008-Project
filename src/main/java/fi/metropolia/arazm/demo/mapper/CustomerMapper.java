package fi.metropolia.arazm.demo.mapper;

import java.util.List;

import fi.metropolia.arazm.demo.dto.rder.OrderDTO;
import fi.metropolia.arazm.demo.dto.rder.OrderItemDTO;
import fi.metropolia.arazm.demo.dto.ustomer.CustomerDTO;
import fi.metropolia.arazm.demo.entity.Customer;
import fi.metropolia.arazm.demo.entity.OrderItem;
import fi.metropolia.arazm.demo.entity.Orders;

public class CustomerMapper {

    public static CustomerDTO toDto(Customer customer){
        List<OrderDTO> orders = customer.getOrders() == null
                ? List.of()
                : customer.getOrders().stream()
                    .map(CustomerMapper::toOrderDto)
                    .toList();

        return new CustomerDTO(
            customer.getId(),
            customer.getFirstName(),
            customer.getLastName(),
            customer.getEmail(),
            customer.getPhone(),
            orders
        );
    }


    private static OrderDTO toOrderDto(Orders order){
        List<OrderItemDTO> items = order.getOrderItems() == null
                    ? List.of()
                    : order.getOrderItems().stream()
                        .map(CustomerMapper::toOrderItemDTO)
                        .toList();

        return new OrderDTO(
            order.getId(),
            order.getStatus(),
            order.getOrderDate(),
            order.getDeliveryDate(),
            order.getCustomer() == null ? null : order.getCustomer().getId(),
            order.getShippingAddress() == null ? null : order.getShippingAddress().getId(),
            items
        );
    }


    private static OrderItemDTO toOrderItemDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getProduct() == null ? null : item.getProduct().getId(),
                item.getProduct() == null ? null : item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }
    
}
