package fi.metropolia.arazm.demo.dto.Order;

import fi.metropolia.arazm.demo.enums.OrderStatus;

public record StatusUpdateRequest(
    OrderStatus status
) {}