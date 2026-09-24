package com.miempresa.microserviceinventory.controller;

import com.miempresa.microserviceinventory.dto.InventoryDTO;
import com.miempresa.microserviceinventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService service;

    @Autowired
    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<InventoryDTO>> getAllStocks(){

        List<InventoryDTO> inventory = service.getAllStocks();

        return inventory.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(inventory);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getStockById(@PathVariable Long id) {
        return service.getStockById(id)
                .map(dto -> ResponseEntity.ok(dto.stock()))
                .orElseThrow();
    }

    @PostMapping
    public ResponseEntity<Long> postStock(@RequestBody InventoryDTO request){

        InventoryDTO dto = service.createStock(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto.stock());
    }
}
