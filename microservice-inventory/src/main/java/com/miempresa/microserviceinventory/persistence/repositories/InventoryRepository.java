package com.miempresa.microserviceinventory.persistence.repositories;

import com.miempresa.microserviceinventory.persistence.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
}
