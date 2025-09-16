package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateCommand {
    private String productId;

    private String name;
    
    private String description;

    private Double price;

    @Positive(message = "Stock must be a positive value")
    private Integer stock;

    private String categoryId;
    
}
