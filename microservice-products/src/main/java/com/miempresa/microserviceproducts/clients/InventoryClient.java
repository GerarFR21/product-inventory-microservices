package com.miempresa.microserviceproducts.clients;

import com.miempresa.microserviceproducts.dto.InventoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-client", url = "${inventory.service.url}")
public interface InventoryClient {

    @GetMapping("/stocks")
    ResponseEntity<List<InventoryDTO>> getAllStocks();

    @GetMapping("/product/{id}")
    ResponseEntity<Long> getStock(@PathVariable long id);

    @PostMapping
    ResponseEntity<Long> createStock(@RequestBody InventoryDTO request);
}
