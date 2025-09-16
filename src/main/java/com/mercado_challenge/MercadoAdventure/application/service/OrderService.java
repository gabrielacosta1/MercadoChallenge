package com.mercado_challenge.MercadoAdventure.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado_challenge.MercadoAdventure.application.port.in.OrderPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.OrderCreationCommand;
import com.mercado_challenge.MercadoAdventure.application.port.out.OrderPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;

@Service
public class OrderService implements OrderPort {

    @Autowired
    private OrderPersistencePort orderPersistencePort;
    
    @Override
    public Order createOrder(OrderCreationCommand command) {
        Order newOrder = Order.createFromCommand(command);
        return orderPersistencePort.save(newOrder);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderPersistencePort.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);

        return orderPersistencePort.save(order);
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderPersistencePort.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderPersistencePort.findByUserId(userId);
    }
}