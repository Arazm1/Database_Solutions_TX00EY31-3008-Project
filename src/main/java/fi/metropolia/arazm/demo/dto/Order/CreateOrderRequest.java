package fi.metropolia.arazm.demo.dto.Order;

import java.util.List;

public record CreateOrderRequest(
        Integer customerId,
        Integer shippingAddressId,
        List<Item> items
) {
    public record Item(Integer productId, Integer quantity) {}
}