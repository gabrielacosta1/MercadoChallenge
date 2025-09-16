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
public class ReviewCreationCommand {
    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    @Positive(message = "Rating must be a positive value")
    @NotNull(message = "Rating cannot be null")
    private int rating;

}
