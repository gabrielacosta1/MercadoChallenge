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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    /**
     * Prueba para verificar la creación de un nuevo pedido.
     */
    @Test
    void testCreateOrder() {
        when(orderPersistencePort.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(creationCommand);

        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        assertEquals(creationCommand.getUserId(), result.getUserId());
        verify(orderPersistencePort, times(1)).save(any(Order.class));
    }

    /**
     * Prueba para verificar la actualización del estado de un pedido cuando el pedido existe.
     */
    @Test
    void testUpdateOrderStatus_whenOrderExists() {
        when(orderPersistencePort.findById("order1")).thenReturn(Optional.of(order));
        when(orderPersistencePort.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus("order1", OrderStatus.SHIPPED);

        assertNotNull(result);
        assertEquals(OrderStatus.SHIPPED, result.getStatus());
        verify(orderPersistencePort, times(1)).findById("order1");
        verify(orderPersistencePort, times(1)).save(order);
    }

    /**
     * Prueba para verificar que se lanza una excepción al intentar actualizar el estado de un pedido que no existe.
     */
    @Test
    void testUpdateOrderStatus_whenOrderNotFound_thenThrowException() {
        when(orderPersistencePort.findById("order1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.updateOrderStatus("order1", OrderStatus.SHIPPED);
        });

        verify(orderPersistencePort, times(1)).findById("order1");
        verify(orderPersistencePort, never()).save(any(Order.class));
    }
}
