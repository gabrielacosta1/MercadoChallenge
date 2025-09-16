package com.mercado_challenge.MercadoAdventure.application.port.out;

import java.util.List;
import java.util.Optional;

import com.mercado_challenge.MercadoAdventure.domain.model.Cart;

public interface CartPersistencePort {
    Optional <Cart> findById(String cartId);
    void addProductToCart(Cart cart);
    void removeProductFromCart(String cartId, String productId);
    Cart save(Cart cart);
    void deleteCart(Cart cart);
    Optional<Cart> findByUserId(String userId);
    void clearCart(String userId);
    List<Cart> findAll();
}
