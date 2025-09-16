package com.mercado_challenge.MercadoAdventure.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationCommand {
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "Product description cannot be empty")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @NotNull(message = "Stock cannot be null")
    @Positive(message = "Stock must be a positive value")
    private int stock;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Category ID cannot be empty")
    private String categoryId;
    
}
