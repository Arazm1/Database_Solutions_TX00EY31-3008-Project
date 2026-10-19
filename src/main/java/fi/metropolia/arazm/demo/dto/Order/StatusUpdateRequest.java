package fi.metropolia.arazm.demo.dto.order;

import fi.metropolia.arazm.demo.common.enums.OrderStatus;

public record StatusUpdateRequest(
    OrderStatus status
) {}