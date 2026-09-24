package com.miempresa.microserviceinventory.service;

import com.miempresa.microserviceinventory.dto.InventoryDTO;
import com.miempresa.microserviceinventory.persistence.entities.InventoryEntity;
import com.miempresa.microserviceinventory.persistence.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository repository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InventoryDTO> getAllStocks() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<InventoryDTO> getStockById(Long id) {
        return repository.findById(id)
                .map(this::toDto);
    }

    @Override
    public InventoryDTO createStock(InventoryDTO request) {
        return toDto(repository.save(toEntity(request)));
    }

    private InventoryDTO toDto(InventoryEntity entity){
        Long stock = entity.getStock() == null ? 0L : entity.getStock();
        return new InventoryDTO(entity.getId(), stock);
    }

    private InventoryEntity toEntity(InventoryDTO request){
        return new InventoryEntity(request.id(), request.stock());
    }


}
