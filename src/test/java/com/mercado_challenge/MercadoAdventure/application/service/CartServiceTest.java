package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.AddToCartCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.query.GetCartQuery;
import com.mercado_challenge.MercadoAdventure.application.port.out.CartPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.OrderPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartPersistencePort cartPersistencePort;
    @Mock
    private OrderPersistencePort orderPersistencePort;
    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private AddToCartCommand addToCartCommand;

    @BeforeEach
    void setUp() {
        cart = new Cart("cart1", "user1", new ArrayList<>());
        addToCartCommand = new AddToCartCommand("prod1", "user1", "cart1", 2);
    }

    /**
     * Prueba para verificar la creación de un nuevo carrito cuando no existe uno para el usuario.
     */
    @Test
    void testCreateCart_whenCartDoesNotExist() {
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.empty());
        when(cartPersistencePort.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.createCart("user1");

        assertNotNull(result);
        assertEquals(cart.getCartId(), result.getCartId());
        verify(cartPersistencePort, times(1)).findByUserId("user1");
        verify(cartPersistencePort, times(1)).save(any(Cart.class));
    }

    /**
     * Prueba para verificar que no se crea un nuevo carrito si ya existe uno para el usuario.
     */
    @Test
    void testCreateCart_whenCartExists() {
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.of(cart));

        Cart result = cartService.createCart("user1");

        assertNotNull(result);
        assertEquals(cart.getCartId(), result.getCartId());
        verify(cartPersistencePort, times(1)).findByUserId("user1");
        verify(cartPersistencePort, never()).save(any(Cart.class));
    }

    /**
     * Prueba para verificar la funcionalidad de añadir un artículo a un carrito existente.
     */
    @Test
    void testAddItemToCart() {
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.of(cart));
        when(cartPersistencePort.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.addItemToCart(addToCartCommand);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        verify(cartPersistencePort, times(1)).save(any(Cart.class));
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando se intenta eliminar un artículo de un carrito que no existe.
     */
    @Test
    void testRemoveItemFromCart_whenCartNotFound_thenThrowException() {
        when(cartPersistencePort.findById("cart1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            cartService.removeItemFromCart("cart1", "prod1");
        });

        verify(cartPersistencePort, times(1)).findById("cart1");
        verify(cartPersistencePort, never()).save(any(Cart.class));
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando se intenta recuperar un carrito que no existe.
     */
    @Test
    void testGetCart_whenCartNotFound_thenThrowException() {
        GetCartQuery query = new GetCartQuery("user1");
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            cartService.getCart(query);
        });

        verify(cartPersistencePort, times(1)).findByUserId("user1");
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando se intenta vaciar un carrito que no existe.
     */
    @Test
    void testClearCart_whenCartNotFound_thenThrowException() {
        when(cartPersistencePort.findByUserId("user1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            cartService.clearCart("user1");
        });

        verify(cartPersistencePort, times(1)).findByUserId("user1");
        verify(cartPersistencePort, never()).save(any(Cart.class));
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando se intenta finalizar una compra para un carrito que no existe.
     */
    @Test
    void testFinishBuy_whenCartNotFound_thenThrowException() {
        when(cartPersistencePort.findById("cart1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            cartService.finishBuy("user1", "cart1");
        });
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando un usuario no autorizado intenta finalizar una compra.
     */
    @Test
    void testFinishBuy_whenUserNotAuthorized_thenThrowException() {
        when(cartPersistencePort.findById("cart1")).thenReturn(Optional.of(cart));

        assertThrows(SecurityException.class, () -> {
            cartService.finishBuy("user2", "cart1");
        });
    }

    /**
     * Prueba para verificar que se lanza una excepción cuando se intenta finalizar una compra para un carrito vacío.
     */
    @Test
    void testFinishBuy_whenCartIsEmpty_thenThrowException() {
        when(cartPersistencePort.findById("cart1")).thenReturn(Optional.of(cart));

        assertThrows(IllegalStateException.class, () -> {
            cartService.finishBuy("user1", "cart1");
        });
    }
}
