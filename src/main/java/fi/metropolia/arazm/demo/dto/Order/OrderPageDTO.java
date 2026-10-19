package fi.metropolia.arazm.demo.dto.order;

import java.util.List;

public record OrderPageDTO(
    List<OrderDto> orders,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
