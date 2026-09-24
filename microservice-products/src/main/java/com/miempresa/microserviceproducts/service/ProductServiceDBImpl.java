package com.miempresa.microserviceproducts.service;

import com.miempresa.microserviceproducts.clients.InventoryClient;
import com.miempresa.microserviceproducts.dto.InventoryDTO;
import com.miempresa.microserviceproducts.dto.ProductRequestDTO;
import com.miempresa.microserviceproducts.dto.ProductResponseDTO;
import com.miempresa.microserviceproducts.exceptions.InternalServerErrorException;
import com.miempresa.microserviceproducts.persistence.entities.ProductEntity;
import com.miempresa.microserviceproducts.persistence.repositories.ProductRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "service.products", havingValue = "db")
public class ProductServiceDBImpl implements ProductService {

    private final ProductRepository repository;
    private final InventoryClient client;

    @Autowired
    public ProductServiceDBImpl(ProductRepository repository, InventoryClient client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        List<ProductEntity> products = repository.findAll();

        if (products.isEmpty()) return List.of();

        try {
            List<InventoryDTO> inventory = client.getAllStocks().getBody();

            if (Optional.ofNullable(inventory).isEmpty()) inventory = List.of();

            Map<Long, InventoryDTO> stocks = inventory.stream()
                    .collect(Collectors.toMap(InventoryDTO::id, i -> i));

            return products.stream()
                    .map(p -> {
                        Optional<InventoryDTO> invDto = Optional.ofNullable(stocks.get(p.getId()));
                        Long stock = invDto.isPresent() ? invDto.get().stock() : 0L;

                        return toDto(p, stock);
                    })
                    .toList();

        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public Optional<ProductResponseDTO> getProductById(long id) {

        Optional<ProductEntity> foundProduct = repository.findById(id);
        Long stock;

        if (foundProduct.isEmpty()) return Optional.empty();

        try {
            stock = client.getStock(id).getBody();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return Optional.of(toDto(foundProduct.get(), stock));
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        ProductEntity entity = repository.save(toEntity(request));
        Long createdStock;

        try {

            createdStock = client.createStock(
                    toInventoryDto(entity.getId(), request.stock())).getBody();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return toDto(entity, createdStock);
    }

    @Override
    public Optional<ProductResponseDTO> updateProduct(long id, ProductRequestDTO request) {

        if (repository.existsById(id)) {
            ProductEntity entity = toEntity(request);
            entity.setId(id);

            return Optional.of(toDto(repository.save(entity), null));
        }

        return Optional.empty();
    }

    @Override
    public Optional<ProductResponseDTO> partiallyUpdateProduct(long id, ProductRequestDTO requestDTO) {

        Optional<ProductEntity> productFound = repository.findById(id);

        if (productFound.isEmpty()) return Optional.empty();

        Optional.ofNullable(requestDTO.name()).ifPresent(n -> productFound.get().setName(n));
        Optional.ofNullable(requestDTO.price()).ifPresent(p -> productFound.get().setPrice(p));

        return Optional.of(toDto(repository.save(productFound.get()), null));
    }

    @Override
    public boolean deleteProduct(long id) {
        if (!repository.existsById(id))
            return false;

        repository.deleteById(id);
        return true;
    }

    private ProductResponseDTO toDto(ProductEntity entity, Long stock) {
        return new ProductResponseDTO(entity.getId(), entity.getName(), entity.getPrice(), stock);
    }

    private ProductEntity toEntity(ProductRequestDTO request) {

        ProductEntity entity = new ProductEntity();
        entity.setName(request.name());
        entity.setPrice(request.price());

        return entity;
    }

    private InventoryDTO toInventoryDto(Long id, Long stock) {
        return new InventoryDTO(id, stock);
    }
}
