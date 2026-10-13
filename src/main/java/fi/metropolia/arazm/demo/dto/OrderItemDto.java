package fi.metropolia.arazm.demo.dto;

public record OrderItemDto(
        Integer productId,
        String productName,
        Integer quantity,
        Double unitPrice
) {}