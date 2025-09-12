package com.mercado_challenge.MercadoAdventure.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private List<OrderItem> items;
    private Double totalAmount;
    private String status;

}
