package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.in.web;

import com.mercado_challenge.MercadoAdventure.application.port.in.OrderPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.OrderCreationCommand;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderPort orderPort;

    public OrderController(OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreationCommand command) {
        Order newOrder = orderPort.createOrder(command);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderPort.updateOrderStatus(orderId, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(orderPort.getOrderById(orderId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(orderPort.getOrdersByUserId(userId));
    }
}
