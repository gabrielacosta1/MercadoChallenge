package com.mercado_challenge.MercadoAdventure.application.port.in;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.OrderCreationCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;

public interface OrderPort {
    Order createOrder(OrderCreationCommand command);
    Order updateOrderStatus(String orderId, OrderStatus newStatus);
    Order getOrderById(String orderId);
    List<Order> getOrdersByUserId(String userId);
}
