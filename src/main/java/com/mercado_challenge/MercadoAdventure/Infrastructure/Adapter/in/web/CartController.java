package com.mercado_challenge.MercadoAdventure.Infrastructure.Adapter.in.web;

import com.mercado_challenge.MercadoAdventure.application.port.in.CartPort;
import com.mercado_challenge.MercadoAdventure.application.port.in.command.AddToCartCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.query.GetCartQuery;
import com.mercado_challenge.MercadoAdventure.domain.model.Cart;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartPort cartPort;

    public CartController(CartPort cartPort) {
        this.cartPort = cartPort;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable String userId) {
        Cart newCart = cartPort.createCart(userId);
        return new ResponseEntity<>(newCart, HttpStatus.CREATED);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItemToCart(@Valid @RequestBody AddToCartCommand command) {
        Cart updatedCart = cartPort.addItemToCart(command);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable String cartId, @PathVariable String productId) {
        Cart updatedCart = cartPort.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable String userId) {
        GetCartQuery query = new GetCartQuery(userId);
        Cart cart = cartPort.getCart(query);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable String userId) {
        cartPort.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/finish-buy")
    public ResponseEntity<Order> finishBuy(@RequestParam String userId, @RequestParam String cartId) {
        Order order = cartPort.finishBuy(userId, cartId);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Cart>> getAllCarts() {
        return ResponseEntity.ok(cartPort.getAllCarts());
    }
}
