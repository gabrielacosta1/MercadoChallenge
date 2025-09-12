package com.mercado_challenge.MercadoAdventure.Domain.Model;

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
}
