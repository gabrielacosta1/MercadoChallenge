package com.mercado_challenge.MercadoAdventure.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private String productId;
    private String userId;
    private int quantity;
    private Double price;
}
