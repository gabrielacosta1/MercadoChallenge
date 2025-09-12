package com.mercado_challenge.MercadoAdventure.Domain.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String name;
    private String description;
    private Double price;
    private int stock;
    private String userId;
    private String categoryId;
    private List<Review> reviews;
}
