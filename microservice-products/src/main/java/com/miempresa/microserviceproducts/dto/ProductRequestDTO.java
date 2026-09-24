package com.miempresa.microserviceproducts.dto;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        BigDecimal price,
        Long stock
) {
}
