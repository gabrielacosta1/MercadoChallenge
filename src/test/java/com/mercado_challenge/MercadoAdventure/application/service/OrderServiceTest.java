package com.mercado_challenge.MercadoAdventure.application.service;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.OrderCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.OrderPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderPersistencePort orderPersistencePort;

    @InjectMocks
    private OrderService orderService;

    private OrderCreationCommand creationCommand;
    private Order order;

    @BeforeEach
    void setUp() {
        creationCommand = new OrderCreationCommand(
                "user1",
                Collections.emptyList(),
                OrderStatus.PENDING,
                100.0
        );

        order = new Order();
        order.setOrderId("order1");
        order.setUserId(creationCommand.getUserId());
        order.setStatus(creationCommand.getStatus());
    }

    @Test
    void testCreateOrder() {
        // Arrange
        when(orderPersistencePort.save(any(Order.class))).thenReturn(order);

        // Act
        Order result = orderService.createOrder(creationCommand);

        // Assert
        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        assertEquals(creationCommand.getUserId(), result.getUserId());
    }
}
