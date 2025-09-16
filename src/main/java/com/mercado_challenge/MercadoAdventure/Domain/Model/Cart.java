package com.mercado_challenge.MercadoAdventure.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String CartId;
    private String userId;
    private List<CartItem> items;

    public void addItem(CartItem newItem) {
        if (this.items == null) {
            this.items = new java.util.ArrayList<>();
        }
        // Reviso que el producto exista, para sumar cantidad
        for (CartItem item : this.items) {
            if (item.getProductId().equals(newItem.getProductId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        // Sino agrego el item
        this.items.add(newItem);
    }
}
