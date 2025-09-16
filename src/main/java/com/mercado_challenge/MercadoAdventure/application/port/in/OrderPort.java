package com.mercado_challenge.MercadoAdventure.application.port.in;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.OrderCreationCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;

public interface OrderPort {
    Order createOrder(OrderCreationCommand command);
    Order updateOrderStatus(String orderId, OrderStatus newStatus);
}
