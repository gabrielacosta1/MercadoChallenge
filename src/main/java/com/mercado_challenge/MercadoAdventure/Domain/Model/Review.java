package com.mercado_challenge.MercadoAdventure.Domain.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    private String reviewId;
    private String userId;
    private int rating;
    private String comment;

}
