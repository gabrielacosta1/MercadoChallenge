package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.out.CartPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartPersistencePort cartPersistencePort;

    @InjectMocks
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
        cart.setCartId("cart1");
        cart.setUserId("user1");
    }

    @Test
    void testCreateCart_whenCartDoesNotExist() {
        // Arrange
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.empty());
        when(cartPersistencePort.save(any(Cart.class))).thenReturn(cart);

        // Act
        Cart result = cartService.createCart("user1");

        // Assert
        assertNotNull(result);
        assertEquals(cart.getCartId(), result.getCartId());
    }

    @Test
    void testCreateCart_whenCartExists() {
        // Arrange
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.of(cart));

        // Act
        Cart result = cartService.createCart("user1");

        // Assert
        assertNotNull(result);
        assertEquals(cart.getCartId(), result.getCartId());
    }
}
