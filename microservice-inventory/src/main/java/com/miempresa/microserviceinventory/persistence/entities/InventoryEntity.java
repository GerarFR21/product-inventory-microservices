package com.miempresa.microserviceinventory.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntity {

    @Id
    private Long id;
    private Long stock;
}
