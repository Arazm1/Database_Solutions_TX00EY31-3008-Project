package fi.metropolia.arazm.demo.dto.Order;

public record OrderItemDto(
        Integer productId,
        String productName,
        Integer quantity,
        Double unitPrice
) {}