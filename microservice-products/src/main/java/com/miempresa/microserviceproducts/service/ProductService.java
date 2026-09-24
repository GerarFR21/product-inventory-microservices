package com.miempresa.microserviceproducts.service;

import com.miempresa.microserviceproducts.dto.ProductRequestDTO;
import com.miempresa.microserviceproducts.dto.ProductResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    Optional<ProductResponseDTO> getProductById(long id);

    ProductResponseDTO createProduct(ProductRequestDTO request);

    Optional<ProductResponseDTO> updateProduct(long id, ProductRequestDTO request);

    Optional<ProductResponseDTO> partiallyUpdateProduct(long id, ProductRequestDTO requestDTO);

    boolean deleteProduct(long id);
}
