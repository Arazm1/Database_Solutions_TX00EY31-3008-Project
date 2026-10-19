package fi.metropolia.arazm.demo.dto.order;

public record OrderItemDTO(
        Integer productId,
        String productName,
        Integer quantity,
        Double unitPrice
) {}