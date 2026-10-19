package fi.metropolia.arazm.demo.dto.rder;

public record OrderItemDTO(
        Integer productId,
        String productName,
        Integer quantity,
        Double unitPrice
) {}