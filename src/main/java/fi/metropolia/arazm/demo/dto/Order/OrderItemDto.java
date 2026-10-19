package fi.metropolia.arazm.demo.dto.order;

public record OrderItemDto(
        Integer productId,
        String productName,
        Integer quantity,
        Double unitPrice
) {}