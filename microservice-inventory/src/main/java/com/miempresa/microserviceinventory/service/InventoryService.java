package com.miempresa.microserviceinventory.service;

import com.miempresa.microserviceinventory.dto.InventoryDTO;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<InventoryDTO> getAllStocks();

    Optional<InventoryDTO> getStockById(Long id);

    InventoryDTO createStock(InventoryDTO request);
}
