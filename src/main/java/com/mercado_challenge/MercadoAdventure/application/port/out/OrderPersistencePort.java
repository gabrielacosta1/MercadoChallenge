package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.Order;

public interface OrderPersistencePort {
    Optional <Order> findById(String orderId);
    Order save(Order order);
    void deleteById(String orderId);
    List<Order> findByUserId(String userId);
    List<Order> findAll();
}
