package fi.metropolia.arazm.demo.dto.order;

import fi.metropolia.arazm.demo.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(
    @NotNull(message = "Order status is required")
    OrderStatus status
) {}