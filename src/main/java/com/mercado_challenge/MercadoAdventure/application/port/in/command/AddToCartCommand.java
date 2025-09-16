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
public class AddToCartCommand {
    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Card ID cannot be empty")
    private String cartId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be a positive value")
    private int quantity;
    
}
