package com.miempresa.microserviceproducts.persistence.repositories;

import com.miempresa.microserviceproducts.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
