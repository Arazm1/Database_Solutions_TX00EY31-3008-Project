package fi.metropolia.arazm.demo.dto.Order;

import java.time.LocalDateTime;
import java.util.List;

import fi.metropolia.arazm.demo.common.enums.OrderStatus;

public record OrderDto(
        Integer id,
        OrderStatus status,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        Integer customerId,
        Integer shippingAddressId,
        List<OrderItemDto> items
) {}