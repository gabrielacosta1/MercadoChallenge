package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.Product;

public interface ProductPersistencePort {
    Optional<Product> findById(String productId);
    Product save(Product product);
    List<Product> findAll();
    List<Product> findByUserId(String userId);
    void deleteById(String productId);
}
