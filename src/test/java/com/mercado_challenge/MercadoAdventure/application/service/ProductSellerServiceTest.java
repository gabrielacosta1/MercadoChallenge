package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductUpdateCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductSellerServiceTest {

    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductSellerService productSellerService;

    private ProductCreationCommand creationCommand;
    private ProductUpdateCommand updateCommand;
    private Product product;

    @BeforeEach
    void setUp() {
        creationCommand = new ProductCreationCommand(
                "user1",
                "A great product",
                99.99,
                10,
                "cat1",
                "Test Product"
        );

        product = new Product();
        product.setProductId("prod1");
        product.setName(creationCommand.getName());
        product.setPrice(creationCommand.getPrice());

        updateCommand = new ProductUpdateCommand(
                "prod1",
                "Updated Product",
                "An updated product",
                129.99,
                5,
                "cat2"
        );
    }

    /**
     * Prueba para verificar la funcionalidad de vender un nuevo producto.
     */
    @Test
    void testSellProduct() {
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        Product result = productSellerService.sellProduct(creationCommand);

        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(creationCommand.getName(), result.getName());
        verify(productPersistencePort, times(1)).save(any(Product.class));
    }

    /**
     * Prueba para verificar la actualización de un producto existente.
     */
    @Test
    void testUpdateProduct_whenProductExists() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.of(product));
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        Product result = productSellerService.updateProduct(updateCommand);

        assertNotNull(result);
        assertEquals(updateCommand.getName(), result.getName());
        verify(productPersistencePort, times(1)).findById("prod1");
        verify(productPersistencePort, times(1)).save(product);
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar actualizar un producto que no existe.
     */
    @Test
    void testUpdateProduct_whenProductNotFound_thenThrowException() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productSellerService.updateProduct(updateCommand);
        });

        verify(productPersistencePort, times(1)).findById("prod1");
        verify(productPersistencePort, never()).save(any(Product.class));
    }

    /**
     * Prueba para verificar la eliminación de un producto existente.
     */
    @Test
    void testDeleteProduct_whenProductExists() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.of(product));
        doNothing().when(productPersistencePort).deleteById("prod1");

        productSellerService.deleteProduct("prod1", "password");

        verify(productPersistencePort, times(1)).findById("prod1");
        verify(productPersistencePort, times(1)).deleteById("prod1");
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar eliminar un producto que no existe.
     */
    @Test
    void testDeleteProduct_whenProductNotFound_thenThrowException() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productSellerService.deleteProduct("prod1", "password");
        });

        verify(productPersistencePort, times(1)).findById("prod1");
        verify(productPersistencePort, never()).deleteById(anyString());
    }

    /**
     * Prueba para verificar la recuperación de productos para un usuario dado cuando existen productos.
     */
    @Test
    void testGetMyProducts_whenProductsExist() {
        when(productPersistencePort.findByUserId("user1")).thenReturn(Collections.singletonList(product));

        var result = productSellerService.getMyProducts("user1", "password");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(productPersistencePort, times(1)).findByUserId("user1");
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar recuperar productos para un usuario que no tiene productos.
     */
    @Test
    void testGetMyProducts_whenNoProductsFound_thenThrowException() {
        when(productPersistencePort.findByUserId("user1")).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> {
            productSellerService.getMyProducts("user1", "password");
        });

        verify(productPersistencePort, times(1)).findByUserId("user1");
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar recuperar un producto por ID que no existe.
     */
    @Test
    void testGetProductById_whenProductNotFound_thenThrowException() {
        when(productPersistencePort.findById("prod1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            productSellerService.getProductById("prod1");
        });

        verify(productPersistencePort, times(1)).findById("prod1");
    }
}
