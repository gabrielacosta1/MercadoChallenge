package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ProductCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSellerServiceTest {

    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductSellerService productSellerService;

    private ProductCreationCommand creationCommand;
    private Product product;

    @BeforeEach
    void setUp() {
        creationCommand = new ProductCreationCommand(
                "Producto Prueba",
                "Es un producto de prueba",
                99.99,
                10,
                "user1",
                "Categoria 1"
        );

        product = new Product();
        product.setProductId("prod1");
        product.setName(creationCommand.getName());
        product.setPrice(creationCommand.getPrice());
    }

    @Test
    void testSellProduct() {
        // Arrange
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        // Act
        Product result = productSellerService.sellProduct(creationCommand);

        // Assert
        assertNotNull(result);
        assertEquals(product.getProductId(), result.getProductId());
        assertEquals(creationCommand.getName(), result.getName());
    }
}
