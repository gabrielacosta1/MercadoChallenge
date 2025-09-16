package com.mercado_challenge.MercadoAdventure.domain.model;

import com.mercado_challenge.MercadoAdventure.application.port.in.command.ReviewCreationCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String reviewId;
    private String userId;
    private String productId;
    private int rating;
    private String comment;

    public static Review createFromCommand(ReviewCreationCommand command) {
        return new Review(
            null,
            command.getUserId(),
            command.getProductId(),
            command.getRating(),
            command.getComment()
        );
    }

}
