package com.mercado_challenge.MercadoAdventure.application.port.in;

import java.util.List;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.AddToCartCommand;
import com.mercado_challenge.MercadoAdventure.application.port.in.query.GetCartQuery;
import com.mercado_challenge.MercadoAdventure.domain.model.Cart;
import com.mercado_challenge.MercadoAdventure.domain.model.Order;

public interface CartPort {
    Cart createCart(String userId);
    Cart addItemToCart(AddToCartCommand command);
    Cart removeItemFromCart(String cartId, String productId);
    Cart getCart(GetCartQuery query);
    void clearCart(String userId);
    Order finishBuy(String userId, String cartId);
    List<Cart> getAllCarts();
}