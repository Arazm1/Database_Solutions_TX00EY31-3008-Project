package fi.metropolia.arazm.demo.dto.rder;

import java.util.List;

public record OrderPageDTO(
    List<OrderDTO> orders,
    int page,
    int size,
    long totalElements,
    int totalPages
) {}
