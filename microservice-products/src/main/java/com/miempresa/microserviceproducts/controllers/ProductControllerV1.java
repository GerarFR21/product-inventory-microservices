package com.miempresa.microserviceproducts.controllers;

import com.miempresa.microserviceproducts.dto.ProductRequestDTO;
import com.miempresa.microserviceproducts.dto.ProductResponseDTO;
import com.miempresa.microserviceproducts.exceptions.ResourceNotFoundException;
import com.miempresa.microserviceproducts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerV1 {

    private final ProductService service;

    @Autowired
    public ProductControllerV1(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        List<ProductResponseDTO> products = service.getAllProducts();

        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {

        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + id + " not found"));

    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO request,
                                                            UriComponentsBuilder uriBuilder) {

        ProductResponseDTO newProduct = service.createProduct(request);

        URI location = uriBuilder.path("/api/v1/products/{id}")
                .buildAndExpand(newProduct.id()).toUri();

        return ResponseEntity.created(location).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody ProductRequestDTO request) {

        Optional<ProductResponseDTO> updatedProduct = service.updateProduct(id, request);

        if (updatedProduct.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("Product with id " + id + " not found");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchProduct(@PathVariable long id, @RequestBody ProductRequestDTO request){

        Optional<ProductResponseDTO> updatedProduct = service.partiallyUpdateProduct(id, request);

        if (updatedProduct.isPresent()) {
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("Product with id " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){

        if (service.deleteProduct(id))
            return ResponseEntity.noContent().build();

        throw new ResourceNotFoundException("Product with id " + id + " not found");
    }
}
