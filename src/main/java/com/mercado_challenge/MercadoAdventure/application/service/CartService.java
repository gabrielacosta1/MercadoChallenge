package com.mercado_challenge.MercadoAdventure.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercado_challenge.MercadoAdventure.application.port.in.CartPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.AddToCartCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.query.GetCartQuery;
import com.mercado_challenge.MercadoAdventure.application.port.out.CartPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.OrderPersistencePort;
import com.mercado_challenge.MercadoAdventure.application.port.out.ProductPersistencePort;
import com.mercado_challenge.MercadoAdventure.domain.model.Cart;
import com.mercado_challenge.MercadoAdventure.domain.model.CartItem;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderItem;
import com.mercado_challenge.MercadoAdventure.domain.model.OrderStatus;
import com.mercado_challenge.MercadoAdventure.domain.model.Product;

@Service
public class CartService implements CartPort {

    @Autowired
    private CartPersistencePort cartPersistencePort;

    @Autowired
    private OrderPersistencePort orderPersistencePort;

    @Autowired
    private ProductPersistencePort productPersistencePort;

    @Override
    public Cart createCart(String userId) {
        Optional<Cart> existingCart = cartPersistencePort.findByUserId(userId);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }
        Cart cart = new Cart(null, userId, new java.util.ArrayList<>());
        return cartPersistencePort.save(cart);
    }

    @Override
    public Cart addItemToCart(AddToCartCommand command) {
        Cart cart = cartPersistencePort.findByUserId(command.getUserId())
                .orElse(new Cart(null, command.getUserId(), new java.util.ArrayList<>()));

        CartItem newItem = new CartItem(command.getProductId(), command.getQuantity());
        cart.addItem(newItem);
        return cartPersistencePort.save(cart);
    }

    @Override
    public Cart removeItemFromCart(String cartId, String productId) {
        Cart cart = cartPersistencePort.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        return cartPersistencePort.save(cart);
    }

    @Override
    public Cart getCart(GetCartQuery query) {
        return cartPersistencePort.findByUserId(query.getUserId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + query.getUserId()));
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = cartPersistencePort.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user ID: " + userId));
        cart.getItems().clear();
        cartPersistencePort.save(cart);
    }

    @Override
    public Order finishBuy(String userId, String cartId) {
        // Obtener y validar el carrito
        Cart cart = cartPersistencePort.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        if (!cart.getUserId().equals(userId)) {
            throw new SecurityException("User does not have permission to access this cart.");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order from an empty cart.");
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        // Procesar cada item del carrito, validar stock y calcular totales
        for (CartItem cartItem : cart.getItems()) {
            Product product = productPersistencePort.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + cartItem.getProductId()));

            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalStateException("Not enough stock for product: " + product.getName());
            }

            // Crear el item de la orden
            OrderItem orderItem = new OrderItem(
                    product.getProductId(),
                    userId,
                    cartItem.getQuantity(),
                    product.getPrice());
            orderItems.add(orderItem);

            // Actualizo el total y el stock del producto
            totalAmount += product.getPrice() * cartItem.getQuantity();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productPersistencePort.save(product);
        }

        // Crear la orden y limpiar el carrito
        Order order = new Order(
                null,
                userId,
                orderItems,
                totalAmount,
                OrderStatus.PENDING);

        Order savedOrder = orderPersistencePort.save(order);
        
        // Una vez la orden se creo, se elimina el carrito actual
        cartPersistencePort.deleteCart(cart);

        return savedOrder;
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartPersistencePort.findAll();
    }
}