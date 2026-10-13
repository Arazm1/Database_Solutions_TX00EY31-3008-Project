package fi.metropolia.arazm.demo.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record OrdersDTO(
    Integer id,
    LocalDateTime orderDate,
    LocalDateTime deliveryDate,
    String status,
    Set<ProductDTO> products
) {}