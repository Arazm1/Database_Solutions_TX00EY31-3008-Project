package fi.metropolia.arazm.demo.dto.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Integer id,
        String status,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate,
        Integer customerId,
        Integer shippingAddressId,
        List<OrderItemDto> items
) {}